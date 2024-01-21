import Dependencies._

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "01.21.24.2"
ThisBuild / organization     := "io.github.heavypunk"
ThisBuild / organizationName := "simplehosting"

lazy val root = (project in file("."))
  .settings(
    name := "simple-hosting.controller.client",
    publishTo := Some(Resolver.file("file", new File("./build/publish"))),
    libraryDependencies += munit % Test,
    libraryDependencies += "org.apache.httpcomponents.client5" % "httpclient5"          % "5.2.1",
    libraryDependencies += "com.fasterxml.jackson.module"     %% "jackson-module-scala" % "2.14.1"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
