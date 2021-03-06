import sbt._

object Dependencies {

  object Version {
    val cats                    = "2.6.1"
    val scalaTest               = "3.2.9"
    val scalaCheck              = "1.15.4"
    val scalaTestPlusScalaCheck = "3.2.9.0"
    val disciplineScalaCheck    = "2.1.5"
  }

  object Libraries {

    lazy val scalaTest               = "org.scalatest"     %% "scalatest"            % Version.scalaTest
    lazy val scalaTestFunSuite       = "org.scalatest"     %% "scalatest-funsuite"   % Version.scalaTest
    lazy val scalaTestPropSpec       = "org.scalatest"     %% "scalatest-propspec"   % Version.scalaTest
    lazy val scalaCheck              = "org.scalacheck"    %% "scalacheck"           % Version.scalaCheck
    lazy val scalaTestPlusScalaCheck = "org.scalatestplus" %% "scalacheck-1-15"      % Version.scalaTestPlusScalaCheck
    lazy val disciplineScalaCheck    = "org.typelevel"     %% "discipline-scalatest" % Version.disciplineScalaCheck
    lazy val catsLaws                = "org.typelevel"     %% "cats-laws"            % Version.cats
    lazy val catsKernelLaws          = "org.typelevel"     %% "cats-kernel-laws"     % Version.cats

  }

}
