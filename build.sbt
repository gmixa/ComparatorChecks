import Dependencies._

name := "ComparatorChecks"

version := "0.1"

scalaVersion := "3.2.0"
compileOrder := CompileOrder.JavaThenScala
scalacOptions := Seq("-explain")
libraryDependencies ++= Seq(
  Libraries.scalaTest               % Test,
  Libraries.scalaTestFunSuite       % Test,
  Libraries.scalaTestPropSpec       % Test,
  Libraries.scalaCheck              % Test,
  Libraries.disciplineScalaCheck    % Test,
  Libraries.catsLaws                % Test,
  Libraries.catsKernelLaws          % Test,
  Libraries.guava,
  Libraries.reflections,
  Libraries.classgraph
)