package com.example

import java.io.File._

object Hello extends App {
  def main(args: Array[String]): Unit = {
    testFileClassImport()
    stuffWithBigInteger()
  }

  def testFileClassImport() {
    listRoots().map(println(_))
  }

  def stuffWithBigInteger() = {
    import java.math.BigInteger.{
      ONE => _,
      TEN,
      ZERO => JAVA_ZERO
    }

    // println(ONE)
    println(TEN)
    println(JAVA_ZERO)

  }

}