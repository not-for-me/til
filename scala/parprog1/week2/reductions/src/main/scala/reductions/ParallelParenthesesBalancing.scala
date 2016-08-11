package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {
    def loop(acc: Int, chars: List[Char]): Boolean = chars match {
      case x :: xs if acc < 0 => false
      case x :: xs if x == '(' => loop(acc + 1, xs)
      case x :: xs if x == ')' => loop(acc - 1, xs)
      case x :: xs => loop(acc, xs)
      case _ => acc == 0
    }
    loop(0, chars.toList)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) = {
      def loop(chars: List[Char], arg1: Int, arg2: Int): (Int, Int) = chars match {
        case x :: xs if arg2 > arg1 => (0, -1)
        case x :: xs if x == '(' => loop(xs, arg1 + 1, arg2)
        case x :: xs if x == ')' => loop(xs, arg1, arg2 + 1)
        case x :: xs => loop(xs, arg1, arg2)
        case _ => (arg1, arg2)
      }

      loop(chars.slice(idx, until).toList, 0, 0)
    }

    def reduce(from: Int, until: Int): Int = {
      if (chars.length < threshold) {
        val result = traverse(from, until, 0, 0)
        result._1 - result._2
      } else {
        val mid = (until - from) / 2
        val result = parallel(reduce(from, mid), reduce(mid, until))
        result._1 + result._2
      }
    }

    reduce(0, chars.length) == 0
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
