package futures


import scala.concurrent.ExecutionContext._
import scala.concurrent.{ExecutionContext, Future}

object StockExample extends App {
    implicit val ex = ExecutionContext.global

    List(CSCO, APPLE, HTC).foreach(stockPrice(_))

    sealed trait StockSymbol

    object CSCO extends StockSymbol

    object APPLE extends StockSymbol
    object HTC extends StockSymbol

    case class PriceObtainFailure(s: String) extends RuntimeException
    case class UnknownSymbol(s: String) extends RuntimeException

    def stockPrice(sym: StockSymbol): Unit = {
        val f = Future {
            sym match {
                case CSCO => 33
                case s@APPLE => throw new PriceObtainFailure("failed for APPLE")
                case s@HTC => throw new UnknownSymbol(s.toString)
            }
        }
        f onSuccess { case r =>
            println(s"${Thread.currentThread().getName} stock price: $r")
        }
        f onFailure {
            case PriceObtainFailure(s) => println(s"${Thread.currentThread().getName} can't obtain price for: $s")
            case UnknownSymbol(s) => println(s"${Thread.currentThread().getName} unknown symbol: $s")
        }

    }


    Thread.currentThread().join()

}
