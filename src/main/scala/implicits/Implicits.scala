package implicits



object Implicits extends App{


    val threeSeconds = 3.seconds
    val twoMinutes = 2.minutes
    val twoMin3s = 2.minutes.and(3.seconds)

    println(threeSeconds)
    println(twoMinutes)
    println(twoMin3s)

    implicit def seconds(x:Int):RichInt = new RichInt(x)
}


// represent all time values in millis
class RichInt(x:Int) {
    def seconds = 1000 * x
    def minutes = 1000 * 60 * x
    def and(y:Int) = x + y
}
