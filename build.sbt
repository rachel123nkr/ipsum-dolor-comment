name := """ipsum-dolar-command"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaWs
)
libraryDependencies += ws

PlayKeys.devSettings += "play.server.http.port" -> "8080"