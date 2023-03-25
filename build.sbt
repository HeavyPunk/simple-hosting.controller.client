import Dependencies._

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "simple-hosting.controller.client",
    libraryDependencies += munit % Test,
    libraryDependencies += "org.apache.httpcomponents.client5" % "httpclient5"          % "5.2.1",
    libraryDependencies += "com.fasterxml.jackson.module"     %% "jackson-module-scala" % "2.14.1"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
