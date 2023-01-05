import Dependencies._
import sbtrelease.ReleaseStateTransformations._

name := "ComparatorChecks"

publishTo := Some(Resolver.file("file", new File(".")))

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

scalaVersion := Version.scalaVersion
compileOrder := CompileOrder.JavaThenScala
scalacOptions ++= Seq(
  "-explain",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:_")
javacOptions ++= Seq("-parameters")

libraryDependencies ++= Seq(
  Libraries.scalaTest % Test,
  Libraries.scalaTestFunSuite % Test,
  Libraries.scalaTestPropSpec % Test,
  Libraries.scalaCheck % Test,
  Libraries.disciplineScalaCheck % Test,
  Libraries.catsLaws % Test,
  Libraries.catsKernelLaws % Test,
  Libraries.scalaCompiler,
  Libraries.cats,
  Libraries.classgraph,
  Libraries.scalaLogging,
  Libraries.logback
)