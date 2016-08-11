package org.learningconcurrency
package ch2

object ThreadsMain extends App {
  val t: Thread = Thread.currentThread
  val name = t.getName
  println(s"I am the thread $name")
}

object ThreadCreation extends App {
  class MyThread extends Thread {
    override def run(): Unit = {
      println("New thread runing.")
    }
  }

  val t = new MyThread
  t.start()
  t.join()
  println("New Thread joined.")
}
