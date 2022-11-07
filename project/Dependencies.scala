import sbt._

object Dependencies {

  object Version {
    val cats = "2.8.0"
    val scalaTest = "3.2.14"
    val scalaCheck = "1.17.0"
    val disciplineScalaCheck = "2.2.0"
    val classgraph = "4.8.149"
  }

  object Libraries {

    lazy val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
    lazy val scalaTestFunSuite = "org.scalatest" %% "scalatest-funsuite" % Version.scalaTest
    lazy val scalaTestPropSpec = "org.scalatest" %% "scalatest-propspec" % Version.scalaTest
    lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    lazy val disciplineScalaCheck = "org.typelevel" %% "discipline-scalatest" % Version.disciplineScalaCheck
    lazy val catsLaws = "org.typelevel" %% "cats-laws" % Version.cats
    lazy val catsKernelLaws = "org.typelevel" %% "cats-kernel-laws" % Version.cats
    lazy val classgraph = "io.github.classgraph" % "classgraph" % Version.classgraph
  }

}
