package options

object Environments extends App {

    val port = getEnv("listen.port.1").getOrElse(3000)
    val port2 = getEnv("listen.port.2").getOrElse(999)
    val port3 = getEnv("listen.port.2").orElse {
        val r = scala.util.Random
        Some(r.nextInt(10000)).map(_.toInt)
    }.get

    println(s"port1: $port")
    println(s"port2: $port2")
    println(s"port3: $port3")


    val secondToMillis = (s: String) => s.stripSuffix("s").toInt * 1000
    val readTimeout = getEnv("read.timeout").orElse(Some("3s")).map(secondToMillis)
    val connTimeout = getEnv("conn.timeout").orElse(Some("100s")).map(secondToMillis).get
    println(s"read.timeout: $readTimeout ms")
    println(s"conn.timeout: $connTimeout ms")

    def getEnv(name: String): Option[String] = {
        Option(System.getenv(name))
    }



}
