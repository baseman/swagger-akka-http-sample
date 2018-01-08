import sbt._

object Dependencies {

  // Versions
  final val akkaVersion = "2.5.8"
  final val akkaHttpVersion = "10.1.0-RC1"

  // Libraries
  val actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val stream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val http = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val sprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  val slf4j = "org.slf4j" % "slf4j-simple" % "1.7.25"
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val swagger = "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.11.2"
  val swaggerJaxRs = "io.swagger" % "swagger-jaxrs" % "1.5.17"
  val cors =   "ch.megard" %% "akka-http-cors" % "0.2.2"
  val jdk9Deps = "javax.xml.bind" % "jaxb-api" % "2.3.0" //https://github.com/swagger-akka-http/swagger-akka-http/issues/62

  //Projects
  val restDeps = Seq(actor, stream, http, sprayJson, slf4j, akkaSlf4j, swagger, swaggerJaxRs, cors, jdk9Deps)
}