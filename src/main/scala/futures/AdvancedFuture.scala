package futures


import java.lang.RuntimeException

import scala.concurrent.ExecutionContext._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object AdvancedFuture extends App {
    implicit val ex = ExecutionContext.global


    (1 to 10).foreach(x => generate())


    def generate() = {
        Future {
            val r = scala.util.Random
            r.nextInt(100) match {
                case x if x <= 50 => x
                case _ => throw new RuntimeException("> 50")
            }
        } onComplete {
            case Success(x: Int) => println(x)
            case Failure(y: Throwable) => println(s"failed $y")
        }
    }


//    multiTypeConverter()
    def multiTypeConverter() {
        val f = Future {
            100
        } map {
            case x => x.toChar
        } map {
            case s: Char => println(s)
        }
    }

    Thread.currentThread().join()

}

object FailureMessage

case class SuccessMessage(s: Any)
