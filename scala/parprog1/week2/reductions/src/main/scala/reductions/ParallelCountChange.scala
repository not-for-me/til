package reductions

import common._
import org.scalameter._

object ParallelCountChangeRunner {

  @volatile var seqResult = 0

  @volatile var parResult = 0

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 80,
    Key.verbose -> true
  ) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val amount = 250
    val coins = List(1, 2, 5, 10, 20, 50)
    val seqtime = standardConfig measure {
      seqResult = ParallelCountChange.countChange(amount, coins)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential count time: $seqtime ms")

    def measureParallelCountChange(threshold: ParallelCountChange.Threshold): Unit = {
      val fjtime = standardConfig measure {
        parResult = ParallelCountChange.parCountChange(amount, coins, threshold)
      }
      println(s"parallel result = $parResult")
      println(s"parallel count time: $fjtime ms")
      println(s"speedup: ${seqtime / fjtime}")
    }

    measureParallelCountChange(ParallelCountChange.moneyThreshold(amount))
    measureParallelCountChange(ParallelCountChange.totalCoinsThreshold(coins.length))
    measureParallelCountChange(ParallelCountChange.combinedThreshold(amount, coins))
  }
}

object ParallelCountChange {

  /** Returns the number of ways change can be made from the specified list of
    * coins for the specified amount of money.
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    val sortedCoins = coins.sortBy(-_)

    def _loop(money: Int, coins: List[Int]): Integer = {
      if (money < 0) 0
      else if (money == 0) 1
      else if (coins.isEmpty) 0
      else _loop(money, coins.tail) + _loop(money - coins.head, coins)
    }

    _loop(money, sortedCoins)
  }

  type Threshold = (Int, List[Int]) => Boolean

  /** In parallel, counts the number of ways change can be made from the
    * specified list of coins for the specified amount of money.
    */
  def parCountChange(money: Int, coins: List[Int], threshold: Threshold): Int = {
    if (threshold(money, coins)) countChange(money, coins)
    else {
      if (coins.isEmpty) 0
      else {
        val result = parallel(parCountChange(money, coins.tail, threshold), parCountChange(money - coins.head, coins, threshold))
        result._1 + result._2
      }
    }

  }

  /** Threshold heuristic based on the starting money. */
  def moneyThreshold(startingMoney: Int): Threshold = (money, coins) => (startingMoney * 2) / 3 >= money

  /** Threshold heuristic based on the total number of initial coins. */
  def totalCoinsThreshold(totalCoins: Int): Threshold = (money, coins) => (totalCoins * 2) / 3 >= coins.length || money < 1

  /** Threshold heuristic based on the starting money and the initial list of coins. */
  def combinedThreshold(startingMoney: Int, allCoins: List[Int]): Threshold = (money, coins) => {
    val thresholdNumber = startingMoney * allCoins.length * 2
    val curNumber = money * coins.length

    thresholdNumber >= curNumber
  }
}
