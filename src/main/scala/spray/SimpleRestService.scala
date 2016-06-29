package spray

import java.util.concurrent.TimeUnit

import akka.actor.Actor.Receive
import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.routing.{Directives, HttpServiceActor}
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import Directives._
import spray.http.StatusCodes._

import scala.concurrent.ExecutionContext.global

object SimpleRestService extends App {
    implicit val as = ActorSystem("restservice")
    val httpActor = as.actorOf(Props(ActorA))
    implicit val timeout = Timeout(1, TimeUnit.SECONDS)
    IO(Http) ? Http.Bind(httpActor, "localhost", 8000)
}

object ActorA extends HttpServiceActor {
    val route =
        path("hi") {
            get {
                complete("hello")
            }
        } ~ path("hi" / Segment) { name =>
            complete(s"hello $name")
        } ~ path("generate") {
            headerValueByName("x-gen-char") {
                case v if v.forall(_.isDigit) => complete("a" * v.toInt)
                case _ => complete(BadRequest, "expecting number for x-gen-char value")
            }
        }

    override def receive: Receive = {
        runRoute(route)
    }
}
