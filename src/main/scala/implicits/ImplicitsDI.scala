package implicits

object ImplicitsDI extends App {
    def print(x: String)(implicit formatter: Formatter) = formatter.format(x)

    implicit val lowercaseFormatter = UppercaseFormatter
    println(print("hello guys"))
    println(print("welcome to scala"))
}

trait Formatter {
    def format(x: String):String
}

object UppercaseFormatter extends Formatter {
    def format(x: String):String = x.toUpperCase
}

object LowercaseFormatter extends Formatter {
    def format(x: String):String = x.toLowerCase
}
