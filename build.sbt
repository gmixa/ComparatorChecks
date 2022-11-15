import Dependencies._
import sbtrelease.ReleaseStateTransformations._
name := "ComparatorChecks"

publishTo := Some(Resolver.file("file",  new File( "." )) )

releaseVersionFile := file("version.sbt")

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion
)
scalaVersion := "3.2.1"
compileOrder := CompileOrder.JavaThenScala
scalacOptions := Seq("-explain")
javacOptions := Seq("-parameters")
libraryDependencies ++= Seq(
  Libraries.scalaTest               % Test,
  Libraries.scalaTestFunSuite       % Test,
  Libraries.scalaTestPropSpec       % Test,
  Libraries.scalaCheck              % Test,
  Libraries.disciplineScalaCheck    % Test,
  Libraries.catsLaws                % Test,
  Libraries.catsKernelLaws          % Test,
  Libraries.classgraph,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.4.4"

)