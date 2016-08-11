package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (r == c || c == 0) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def _loop(chars: List[Char], acc: Integer): Boolean = {
      if (chars.isEmpty) acc == 0
      else {
        if (chars.head == '(') _loop(chars.tail, acc + 1)
        else if (chars.head == ')' && acc == 0) false
        else if (chars.head == ')') _loop(chars.tail, acc - 1)
        else _loop(chars.tail, acc)
      }
    }

    _loop(chars, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    val sortedCoins = coins.sortBy(-_)

    def _loop(money: Int, coins: List[Int]): Integer = {
      if (coins.isEmpty) 0
      else if (money < 0) 0
      else if (money == 0) 1
      else _loop(money, coins.tail) + _loop(money - coins.head, coins)
    }

    _loop(money, sortedCoins)
  }
}
