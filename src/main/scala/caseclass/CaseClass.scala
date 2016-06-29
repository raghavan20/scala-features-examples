package caseclass

object CaseClass extends App{
    val p1 = new Person(2, "mike", 28, "tester", "somewhere")
    val p2 = new Person(2, "mike", 28, "tester", "somewhere")
    val p3 = new Person(3, "john", 14, "tester", "somewhere")

    println(p1)
    println(p3)
    println(s"p1 == p2 ${p1 == p2}")
    println(s"p1 == p3 ${p1 == p3}")
}
case class Person(id:Int, name:String, age:Int, profession:String, address: String)