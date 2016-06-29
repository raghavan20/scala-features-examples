organization := "com.freedomain"

name := "scala-team-demo"
version := "0.1"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
    val akkaV = "2.3.9"
    val sprayV = "1.3.3"
    Seq(
        "io.spray" %% "spray-can" % sprayV,
        "io.spray" %% "spray-routing" % sprayV,
        "io.spray" %% "spray-client" % sprayV,
        "io.spray" %% "spray-json" % "1.3.1",
        "io.spray" %% "spray-testkit" % sprayV % "test",

        "com.typesafe.akka" %% "akka-actor" % akkaV,
        "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",

        "commons-io" % "commons-io" % "2.4",
        "commons-lang" % "commons-lang" % "2.6",
        "org.scalatest" %% "scalatest" % "2.2.6" % "test",

        "ch.qos.logback" % "logback-classic" % "1.1.1",
        "ch.qos.logback" % "logback-core" % "1.1.1",
        "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
        "org.slf4j" % "jcl-over-slf4j" % "1.7.5",
        "org.slf4j" % "jul-to-slf4j" % "1.7.5"
    )
}

