package progscala2.introscala.shapes

object Messages {
  object Exit
  object Finished
  case class Response(message: String)
}

import akka.actor.Actor

class ShapesDrawingActor extends Actor {
  import Messages._

  def receive = {
    case s: Shape =>
      s.draw(str => println(s"ShapesDrawingActor: $str"))
      sender ! Response(s"ShapesDrawingActor: $s drawn")
    case Exit =>
      println("ShapesDrawingActor: exiting...")
      sender ! Finished
    case unexpected =>
      val response = Response(s"ERROR: Unknown messge: $unexpected")
      println(s"ShapesDrawingActor: $response")
      sender ! response 
  }
}
