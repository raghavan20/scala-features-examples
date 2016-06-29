import org.scalatest.{FlatSpec, ShouldMatchers}

object Calculator {
    def add(x: Int, y: Int): Int = x + y
}

class CalculatorSpec extends FlatSpec with ShouldMatchers {
    "calculator" should "add two integers" in {
        Calculator.add(3, 5) shouldEqual 8
    }

    it should "be commutative" in {
        Calculator.add(3, 5) shouldEqual Calculator.add(5, 3)
    }

    it should "not be equal when addition results are different" in {
        Calculator.add(3, 5) shouldNot equal(Calculator.add(15, 13))
    }
}
