object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val myList = List(1, "Hello", 0.123)
    myList.foreach(e => println(e))
  }
}
