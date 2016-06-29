package futures


import java.lang.RuntimeException

import scala.concurrent.ExecutionContext._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object AdvancedFuture extends App {
    implicit val ex = ExecutionContext.global

    multiTypeConverter()
    (1 to 10).foreach(x => generate())

    def multiTypeConverter() {
        val f = Future {
            100
        } map {
            case x => x.toChar
        } map {
            case s => s.toFloat
        }
    }

    def generate() = {
        val f = Future {
            val r = scala.util.Random
            r.nextInt(100) match {
                case x if x <= 50 => x
                case _ => throw new RuntimeException("> 50")
            }
        } onComplete {
            case Success(x) => println(x)
            case Failure(y) => println(s"failed $y")
        }
    }

    Thread.currentThread().join()

}

object FailureMessage

case class SuccessMessage(s: Any)
