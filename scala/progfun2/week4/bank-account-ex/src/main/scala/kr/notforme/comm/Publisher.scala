package kr.notforme.comm

/**
  * Publisher.java version 2016, 07. 27
  *
  * Copyright 2016 Ticketmonster Corp. All rights Reserved. 
  * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  */
trait Publisher {
  private var subscribers: Set[Subscriber] = Set()

  def subscribe(subscriber: Subscriber): Unit = subscribers += subscriber

  def unsubscribe(subscriber: Subscriber): Unit = subscribers -= subscriber

  def publish(): Unit = subscribers.foreach(_.handle(this))
}
