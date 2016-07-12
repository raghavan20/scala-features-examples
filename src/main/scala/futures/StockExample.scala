package futures


import scala.concurrent.ExecutionContext._
import scala.concurrent.{ExecutionContext, Future}

object StockExample extends App {
    implicit val ex = ExecutionContext.global


    sealed trait StockSymbol

    object CSCO extends StockSymbol

    object APPLE extends StockSymbol

    object HTC extends StockSymbol


    List(CSCO, APPLE, HTC).foreach(stockPrice(_))

    case class PriceObtainFailure(s: String) extends RuntimeException

    case class UnknownSymbol(s: String) extends RuntimeException

    case class Price(sym: StockSymbol, price: Int)

    def stockPrice(sym: StockSymbol): Unit = {
        val f = Future {
            sym match {
                case CSCO => Price(CSCO, 33)
                case APPLE => throw new PriceObtainFailure(s"failed for APPLE")
                case s@HTC => throw new UnknownSymbol(s.toString)
            }
        }
        f onSuccess { case r: Price =>
            println(s"${Thread.currentThread().getName} purchase 5 units of ${r.sym} @ ${r.price}")
        }
        f onFailure {
            case PriceObtainFailure(s) => println(s"${Thread.currentThread().getName} can't obtain price for: $s")
            case UnknownSymbol(s) => println(s"${Thread.currentThread().getName} unknown symbol: $s")
        }

    }


    Thread.currentThread().join()

}
