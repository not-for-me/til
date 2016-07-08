package controllers

import javax.inject._

import actors.TwitterStreamer
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.{Concurrent, Enumeratee, Enumerator, Iteratee}
import play.api.libs.json.{JsObject, JsValue}
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.extras.iteratees.{Encoding, JsonIteratees}

import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class Application @Inject()(configuration: play.api.Configuration, ws: WSClient) extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { implicit request =>
    Ok(views.html.index("Tweets"))
  }

  def tweets =  WebSocket.acceptWithActor[String, JsValue] {
    request => out => TwitterStreamer.props(out)
  }

  //  def tweets = Action.async {
//
//    credentials.map { case (consumerKey, requestToken) =>
//      val (iteratee, enumerator) = Concurrent.joined[Array[Byte]]
//
//      val jsonStream: Enumerator[JsObject] =
//        enumerator &>
//          Encoding.decode() &>
//          Enumeratee.grouped(JsonIteratees.jsSimpleObject)
//
//      val loggingIteratee = Iteratee.foreach[JsObject] { data =>
//        Logger.info(data.toString)
//      }
//      jsonStream run loggingIteratee
//
//      ws
//        .url("https://stream.twitter.com/1.1/statuses/filter.json")
//        .sign(OAuthCalculator(consumerKey, requestToken))
//        .withQueryString("track" -> "reactive")
//        .get { response =>
//          Logger.info("Status: " + response.status)
//          iteratee
//        }.map { _ =>
//        Ok("Stream closed")
//      }
//    } getOrElse {
//      Future.successful {
//        InternalServerError("Twitter credentials missing")
//      }
//    }
//  }

  def credentials: Option[(ConsumerKey, RequestToken)] = for {
    apiKey <- configuration.getString("twitter.apiKey")
    apiSecret <- configuration.getString("twitter.apiSecret")
    token <- configuration.getString("twitter.token")
    tokenSecret <- configuration.getString("twitter.tokenSecret")
  } yield (
    ConsumerKey(apiKey, apiSecret),
    RequestToken(token, tokenSecret)
    )

}
