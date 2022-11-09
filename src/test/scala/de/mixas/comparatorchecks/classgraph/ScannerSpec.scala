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

  it should "find three classes implementing Comparator in the package 'tests' " in {
    val scanner = Scanner("tests.comparatorAsAttribute")
    val found = scanner.allComparators()
    found match
      case Failure(exception) => fail(exception)
      case Success(value) => value.size should be(1)
  }

  it should "find all methods returning a Comparator in the package 'tests' " in {
    val scanner = Scanner("tests.comparatorAsAttribute")
    val found = scanner.allFieldsDefiningComparators()
    found match
      case Failure(exception) => fail(exception)
      case Success(value) =>
        println(value)
        value.size should be(1)
  }

  ignore should "find all lambdas defining a Comparator in the packafe 'tests' " in {
    val scanner = Scanner("tests.comparatorAsAttribute")
    val found = scanner.allLambdasDefiningAComparator(true)
    found match
      case Failure(exception) => fail(exception)
      case Success(value) =>
        println(value)
        value.size should be(2)
  }

}
