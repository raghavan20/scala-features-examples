package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive

object SimpleActor extends App {
    val actorSys = ActorSystem("simpleActorSys")
    val poA = actorSys.actorOf(Props(new PongResponder), "pongActor")
    val piA = actorSys.actorOf(Props(new PingSender), "pingActor")

    piA ! Start
}

object Pong
object Ping
object Start
object Hello
object UnknownMessage

class PingSender extends  Actor{
    def receive:Receive = {
        case Pong => println(s"received pong from $self")
            sender ! Hello
        case Start =>
            context.actorSelection("akka://simpleActorSys/user/pongActor") ! Ping
    }
}

class PongResponder extends Actor {
    println(s"$self")

    override def receive: Receive = {
        case Ping => println(s"received ping from $self")
            sender ! Pong
        case x:Any =>
            println(s"received unknown message: $x")
            println("shutting down actor system")
            context.system.shutdown()
    }
}
