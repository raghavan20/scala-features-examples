package futures

import scala.concurrent.{Await, Future}

import scala.concurrent.duration.Duration

object SimpleFuture extends App {
    implicit val ex = scala.concurrent.ExecutionContext.global
    val f = Future {
        println(Thread.currentThread().getName)
        33
    }
    val result = Await.result(f , Duration(3, "s"))
    println(result)
}
