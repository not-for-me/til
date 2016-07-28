package kr.notforme.bank

import kr.notforme.comm.Publisher

/**
  * BankAccount.scala
  *
  * @author woojin
  * @since 2016, 07. 27
  */
class BankAccount extends Publisher {
  private var balance = 0

  def currentBalance: Int = balance

  def deposit(amount: Int): Unit =
    if (amount > 0) {
      balance = balance + amount
      publish()
    }

  def withdraw(amount: Int): Unit =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()
    } else throw new Error("insufficient funds")

}
