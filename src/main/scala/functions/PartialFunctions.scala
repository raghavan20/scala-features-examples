package functions

import functions.PartialFunctions.Box

object PartialFunctions extends App {

    case class Box(length: Int, width: Int)

    val h: (Any) => Int = hash(SillyHashers.hashers)_

    println(h(33))
    println(h("hello scala"))
    println(h(Box(3, 4)))


    def hash(hashers: List[PartialFunction[Any, Int]])(o: Any): Int = {
        val hF = hashers.find(_.isDefinedAt(o)).get
        hF(o)
    }
}


object SillyHashers {
    val intHasher: PartialFunction[Any, Int] = {
        case x: Int => x
    }
    val stringHasher: PartialFunction[Any, Int] = {
        case s: String => s.hashCode
    }
    val boxHasher: PartialFunction[Any, Int] = {
        case b: Box => b.length * b.width
    }

    val hashers = List(intHasher, stringHasher, boxHasher)
}