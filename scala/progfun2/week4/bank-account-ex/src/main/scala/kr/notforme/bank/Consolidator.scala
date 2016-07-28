package kr.notforme.bank

import kr.notforme.comm.{Publisher, Subscriber}

/**
  * Consolidator.scala
  *
  * @author woojin
  * @since 2016, 07. 27
  */
class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _
  compute()

  private def compute() = total = observed.map(_.currentBalance).sum()

  def handler(pub: Publisher) = compute()

  def totalBalance = total
}
