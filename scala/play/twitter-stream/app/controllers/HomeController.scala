package controllers

import javax.inject._

import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.Iteratee
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(configuration: play.api.Configuration, ws: WSClient) extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  def tweets = Action.async {
    val loggingIteratee = Iteratee.foreach[Array[Byte]] { array =>
      Logger.info(array.map(_.toChar).mkString)
    }
    credentials.map { case (consumerKey, requestToken) =>
      ws
        .url("https://stream.twitter.com/1.1/statuses/filter.json")
        .sign(OAuthCalculator(consumerKey, requestToken))
        .withQueryString("track" -> "reactive")
        .get { response =>
          Logger.info("Status: " + response.status)
          loggingIteratee
        }.map { _ =>
        Ok("Stream closed")
      }
    } getOrElse {
      Future.successful {
        InternalServerError("Twitter credentials missing")
      }
    }
  }

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
