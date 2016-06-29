package functions

object Transformations extends App{
    val f1 = (x:String) => x.toCharArray
    val f2 = (x:Array[Char]) => x.map(_.toInt)
    val f3 = (x:Array[Int]) => x.foldLeft(0)(_ + _)

    def tf(x:String) = f3(f2(f1(x)))
    def tf2(x:String) = f3.compose(f2.compose(f1))(x)
    def tf3(x:String) = f1.andThen(f2.andThen(f3))(x)
    def tf4 = f1.andThen(f2.andThen(f3))

    println(tf("abc"))
    println(tf2("abc"))
    println(tf3("abc"))
    println(tf4("abc"))
}
