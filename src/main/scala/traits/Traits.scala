package traits

import java.io.{File, FileWriter, PrintWriter}
import java.nio.file.{Files, Paths}

object Traits extends App {
    val c1 = Car("volks", "golf", 1600)
    val c2 = Car("ford", "focus", 1800)
    val cars = List(c1, c2)
    cars.foreach(c => {
        c.log()
        c.persist()
    })
}

case class Car(make: String, model: String, engineSize: Int)  extends Persistable with Stdout

trait Auditable extends Stdout with Persistable

trait Stdout {
    self =>
    def log() = println(self.toString)
}

trait Persistable {
    self =>

    def persist() = {
        val w = new PrintWriter(new FileWriter("/tmp/xadf", true))
        w.append(self.toString)
        w.close()
    }
}