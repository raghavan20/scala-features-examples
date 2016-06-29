package actors

import java.util.Date
import java.util.concurrent.atomic.AtomicInteger

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.ExecutionContext.global


object Shopping extends App {
    val as = ActorSystem("shopping")
    val shopper = as.actorOf(Props(Shopper), "shopper")
    as.actorOf(Props(OrderCreator), "orderCreator")
    as.actorOf(Props(Inventory), "inventory")
    as.actorOf(Props(Dispatcher), "dispatcher")
    as.actorOf(Props(Emailer), "emailer")

    for (i <- 1 to 6) shopper ! Shop


}

object Shop

object ProductNotFound

case class ProductReserved(o: Order)

case class ProductUnavailable(o: Order)

case class OrderCreated(o: Order)

case class Dispatched(o: Order)

case class OrderMessage(product: Product)

case class Order(product: Product, creationTime: Date = new Date()) {
    val id = OrderIdGenerator.get

    override def toString = s"Order[#$id,${product.name}]"
}

object OrderIdGenerator {
    val id = new AtomicInteger(1)

    def get = id.getAndIncrement()
}

case class Product(name: String)

object PS4 extends Product("ps4")

object Shopper extends Actor {
    override def receive: Receive = {
        case shop =>
            val orderCreator = context.actorSelection("akka://shopping/user/orderCreator")
            orderCreator ! OrderMessage(PS4)
            println("sent order to buy ps4")
    }
}

object OrderCreator extends Actor {
    val backlog = List()

    override def receive: Receive = {
        case OrderMessage(p) =>
            val o = Order(p)
            println(s"created order: $o")
            val inventory = context.actorSelection("akka://shopping/user/inventory")
            inventory ! o
        case ProductReserved(o) =>
            val dispatcher = context.actorSelection("akka://shopping/user/dispatcher")
            dispatcher ! o
        case ProductUnavailable(o) =>
            o :: backlog
            println(s"backlogged order: $o; will retry later")
    }
}

object Inventory extends Actor {
    val products = Map((PS4, new AtomicInteger(5)))

    override def receive: Receive = {
        case o@Order(PS4, _) => if (products.get(PS4).get.get() > 0) {
            products.get(PS4).map(_.decrementAndGet())
            println(s"after decrementing, PS4 quantity: ${products.get(PS4).get}")
            sender ! ProductReserved(o)
        } else {
            sender ! ProductUnavailable(o)
        }
        case _ => sender ! ProductNotFound
    }
}

object Dispatcher extends Actor {
    override def receive: Receive = {
        case o: Order => println(s"sending $o to rag's home")
            val emailer = context.actorSelection("akka://shopping/user/emailer")
            emailer ! Dispatched(o)
    }
}

object Emailer extends Actor {
    override def receive: Receive = {
        case Dispatched(o) => println(s"hi user, $o has been dispatched")
        case OrderCreated(o) => println(s"hi user, $o has been created; will be dispatched soon.")
    }
}