package de.mixas.comparatorchecks.classgraph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.{Failure, Success}

class ScannerSpec extends AnyFlatSpec with should.Matchers{

  "A Scanner" should "throw IllegalArgumentException if null is set as param type" in {
    a[IllegalArgumentException] should be thrownBy{
      Scanner(null.asInstanceOf[String])
    }
  }

  it should "find two classes implementing Compareable in the package 'tests' " in {
    val scanner = Scanner("tests")
    val found = scanner.allComparable()
    found match
      case Failure(exception) => fail(exception)
      case Success(value) => value.size should be(2)
  }

  it should "find one class implementing Comparator in the package 'tests' " in {
    val scanner = Scanner("tests.comparatorAsAttribute")
    val found = scanner.allComparators()
    found match
      case Failure(exception) => fail(exception)
      case Success(value) => value.size should be(1)
  }

  it should "find one field returning a Comparator in the package 'tests' " in {
    val scanner = Scanner("tests.comparatorAsAttribute")
    val found = scanner.allFieldsDefiningComparators()
    found match
      case Failure(exception) => fail(exception)
      case Success(value) =>
        value.size should be(1)
  }

  it should "find 3 lambdas defining a Comparator in the packafe 'tests' " in {
    val scanner = Scanner("tests")
    val found = scanner.allLambdasDefiningAComparator(true)
    found match
      case Failure(exception) => fail(exception)
      case Success(value) =>
        value.size should be(3)
  }

}
