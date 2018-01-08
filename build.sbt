
name := "swagger-akka-http-sample"

scalaVersion := "2.12.4"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")

val akkaVersion = "2.5.8"
val akkaHttpVersion = "10.1.0-RC1"

libraryDependencies ++= Seq(
  "io.swagger" % "swagger-jaxrs" % "1.5.17",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.11.2",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.megard" %% "akka-http-cors" % "0.2.2",
  "javax.xml.bind" % "jaxb-api" % "2.3.0", //https://github.com/swagger-akka-http/swagger-akka-http/issues/62
  "org.slf4j" % "slf4j-simple" % "1.7.25"
)

enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("java")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}

//// Define a Dockerfile
//dockerfile in docker := {
//  val artifact: File = assembly.value
//  val artifactTargetPath = s"/app/${artifact.name}"
//
//  new Dockerfile {
//    from("gettyimages/spark:2.0.1-hadoop-2.7")
//
//    // Set the log4j.properties
//    run("mkdir", "-p", "/usr/local/spark/conf")
//    //    env("SPARK_HOME", "/usr/local/spark")
//    stageFile(file("spark/conf/log4j.properties"), file("log4j.properties"))
//    copy("log4j.properties", "/usr/local/spark/conf")
//
//    //add ML model files
//    run("mkdir", "-p", "/app/model")
//    add(file("compose/files/data"), "/app/data")
//
//    add(artifact, artifactTargetPath)
//  }
//}
//
//// sbt dockerFileTask
//// See https://github.com/marcuslonnberg/sbt-docker/issues/34
//
//val dockerFileTask = taskKey[Unit]("Prepare the dockerfile and needed files")
//
//dockerFileTask := {
//  val dockerDir = target.value / "docker"
//
//  val artifact: File = assembly.value
//  val artifactTargetPath = s"/app/${artifact.name}"
//
//  val dockerFile = new Dockerfile {
//
//    from("gettyimages/spark:2.0.1-hadoop-2.7")
//
//    // Set the log4j.properties
//    run("mkdir", "-p", "/usr/local/spark/conf")
//    run("mkdir", "-p", "/usr/local/spark/conf")
//    //    env("SPARK_HOME", "/usr/local/spark")
//    stageFile(file("spark/conf/log4j.properties"), file("log4j.properties"))
//    copy("log4j.properties", "/usr/local/spark/conf")
//
//    //add ML model files
//    run("mkdir", "-p", "/app/model")
//    add(file("files/data"), "/app/data")
//
//    // Add the JAR file
//    add(artifact, artifactTargetPath)
//  }
//
//  val stagedDockerfile = sbtdocker.staging.DefaultDockerfileProcessor(dockerFile, dockerDir)
//  IO.write(dockerDir / "Dockerfile", stagedDockerfile.instructionsString)
//  stagedDockerfile.stageFiles.foreach {
//    case (source, destination) =>
//      source.stage(destination)
//  }
//}
//
//dockerFileTask <<= dockerFileTask.dependsOn(compile in Compile, dockerfile in docker)