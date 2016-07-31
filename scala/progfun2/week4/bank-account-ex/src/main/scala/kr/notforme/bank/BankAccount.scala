package kr.notforme.bank

import kr.notforme.comm.Publisher
import collection._
import immutable._

/**
  * BankAccount.scala
  *
  * @author woojin
  * @since 2016, 07. 27
  */
class BankAccount extends Publisher {
  private var balance = 0
  val test = HashMap.empty
  BitSet

  def currentBalance: Int = balance

  def deposit(amount: Int): Unit =
    if (amount > 0) {
      import java.math.BigInteger.{TEN, TWO, ZERO => JAVA_ZERO, ONE => _}

      println("10 = " + TEN)
      println("2 + 10 = "  + (TEN add TWO))

    }

  def withdraw(amount: Int): Unit =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()
    } else throw new Error("insufficient funds")

}
