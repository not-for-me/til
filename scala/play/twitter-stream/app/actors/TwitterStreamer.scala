package actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, Props}
import play.api.Logger
import play.api.libs.json.Json

/**
  * TwitterStreamer.scala
  *
  * @author woojin
  * @since 2016, 07. 07
  */
class TwitterStreamer(out: ActorRef) extends Actor {
  def receive: Receive = {
    case "subscribe" => Logger.info("Received subscription from a client")
      out ! Json.obj("text" -> "Hello twitter")
  }
}

object TwitterStreamer {
  def props(out: ActorRef) = Props(new TwitterStreamer(out))
}
