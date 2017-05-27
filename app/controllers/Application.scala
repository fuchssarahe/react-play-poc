package controllers

import javax.inject.Inject

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import akka.stream.Materializer
import play.api._
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.mvc._
import play.api.libs.ws._
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (ws: WSClient)(implicit mat: Materializer) extends Controller {

  // http endpoint to check that the server is running
  def index = Action {
    Ok("I'm alive!\n")
  }

  // endpoint that opens an echo websocket
  def wsEcho = WebSocket.using[String] {
    request => {
      Logger.info(s"wsEcho, client connected.")
      var channel: Option[Concurrent.Channel[String]] = None
      val outEnumerator: Enumerator[String] = Concurrent.unicast(c => channel = Some(c))

      val inIteratee: Iteratee[String, Unit] = Iteratee.foreach[String](receivedString => {
        // send string back
        Logger.info(s"wsEcho, received: $receivedString")
        channel.foreach(_.push(receivedString))
      })

      (inIteratee, outEnumerator)
    }
  }

  object EchoWebSocketActor {
    def props(out: ActorRef) = Props(new EchoWebSocketActor(out))
  }

  class EchoWebSocketActor(out: ActorRef) extends Actor {
    def receive = {
      case msg: String =>
        Logger.info(s"actor, received message: $msg")
        if (msg == "goodbye") self ! PoisonPill
        else out ! ("I received your message: " + msg)
    }
  }

  def wsWithActor = WebSocket.acceptWithActor[String, String] {
    request =>
      out => {
        Logger.info("wsWithActor, client connected")
        EchoWebSocketActor.props(out)
      }
  }
}