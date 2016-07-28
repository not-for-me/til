package kr.notforme.comm

/**
  * Subscriber.java version 2016, 07. 27
  *
  * Copyright 2016 Ticketmonster Corp. All rights Reserved. 
  * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  */
trait Subscriber {
  def handle(pub: Publisher)
}
