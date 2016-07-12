package actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.io.Tcp._
import akka.util.{ByteString, CompactByteString}

object HelloTcpServer extends App {
    implicit val as = ActorSystem("hellosys")
    as.actorOf(Props(classOf[ServerHandler]), "serverHandler")
}

class ServerHandler extends Actor {

    import HelloTcpServer.as

    IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9001))

    var unbinder: ActorRef = null

    override def receive: Receive = {
        case Bound(l) => println(s"service bound: $l")
            unbinder = sender
        case Unbound => println("service unbound")
        case Connected(remote, local) =>
            println(s"connection from $remote to $local")
            sender ! Tcp.Register(context.actorOf(Props(classOf[HelloActor])))
        case StopService =>
//            println(s"unbinder: $unbinder")
            unbinder ! Unbind
    }
}

object StopService

class HelloActor extends Actor {
    println(s"obj: ${this.hashCode()}")

    override def receive: Receive = {
        case Received(data: ByteString) =>
            val x = data.utf8String.trim
            println(s"received:$x")
            x match {
                case "hi" => sender ! Write(CompactByteString("hello"))
                case _ => sender ! Write(CompactByteString("i was expecting you to say hi"))
                    context.actorSelection("akka://hellosys/user/serverHandler") ! StopService
            }
        case PeerClosed => println("peer closed")
    }
}
