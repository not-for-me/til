package com.example.pkg

//import java.awt._
//import javax.swing._
//import java.io.File
//import java.io.File._
import java.util.{HashMap, Map}

import com.example.common._

/**
  * PkgExample.scala
  *
  * @author woojin
  * @since 2016, 08. 07
  */
object PkgExample extends App {
  def stuffWithBigInteger() = {
    import java.math.BigInteger.{
    ONE => _,
    TEN,
    ZERO => JAVAZERO }

    // println( "ONE: "+ONE )     // ONE is effectively undefined
    println( "TEN: "+TEN )
    println( "ZERO: "+JAVAZERO )
  }


  // package object
  def runGame() = {
    val outcomes1 = List( (Player("Woojin"), new Scissors), (Player("Donam"), new Rock), (Player("IK"), new Paper) )
    println(outcomes1)

    val outcomes2 = List( (Player("Woojin"), new Scissors), (Player("Donam"), new Rock), (Player("IK"), new Rock) )
    println(takeWinner(outcomes1))
  }

  stuffWithBigInteger()
  runGame()

}
