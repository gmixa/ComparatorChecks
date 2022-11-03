package de.mixas.comparatorchecks.guava

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ScannerSpec extends AnyFlatSpec with should.Matchers {

  ignore should "throw IllegalArgumentException if null is set as param type" in {
    a[IllegalArgumentException] should be thrownBy {
      Scanner(null.asInstanceOf[String])
    }
  }

  ignore should "find one class implementing Compareable in the package 'de' " in {
    val scanner = Scanner("tests")
    val found = scanner.allComparable()
    found.size should be(2)
  }

  ignore should "find the classes implementing Comparator in the package 'de' " in {
    val scanner = Scanner("tests.comparatorIsAttribute")
    val found = scanner.allComparators()
    found.size should be(3)
  }

  ignore should "find all methods returning a Comparator in the package 'de' " in {
    val scanner = Scanner("tests")
    val found = scanner.allFieldsDefiningComparators()
    found.size should be(3)
  }

}
