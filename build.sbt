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

version := "1.0"

//scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akka = "com.typesafe.akka"
  val akkaV = "2.4.10"
  val scalaTestV = "3.0.0"
  Seq(
    akka                        %% "akka-actor"                           % akkaV,
    akka                        %% "akka-testkit"                         % akkaV % "test",
    akka                        %% "akka-slf4j"                           % akkaV,
    akka                        %% "akka-http-core"                       % akkaV,
    akka                        %% "akka-http-experimental"               % akkaV,
    akka                        %% "akka-http-spray-json-experimental"    % akkaV,
    akka                        %% "akka-http-testkit"                    % akkaV,
    akka                        %% "akka-stream-kafka"                    % "0.12",
    "de.heikoseeberger"         %% "akka-sse"                             % "1.10.0",
    "ch.megard"                 %% "akka-http-cors"                       % "0.1.6",
    "ch.qos.logback"            % "logback-classic"                       % "1.1.7",
    "org.codehaus.groovy"       % "groovy"                                % "2.4.7",
    "org.mongodb.scala"         %% "mongo-scala-driver"                   % "1.2.1"
  )
}

// Define a Dockerfile
//dockerfile in docker := {
//
//  new Dockerfile {
//    from("java")
//    add(artifact, artifactTargetPath)
//  }
//}

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

//val dockerFileTask = taskKey[Unit]("Prepare the dockerfile and needed files")
//
//dockerFileTask := {
//  val dockerDir = target.value / "docker"
//
//  val artifact: File = assembly.value
//  val artifactTargetPath = s"/app/${artifact.name}"
//
//  val dockerFile = new Dockerfile {
//    from("java")
//    add(artifact, artifactTargetPath)
//    entryPoint("java", "-jar", artifactTargetPath)
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