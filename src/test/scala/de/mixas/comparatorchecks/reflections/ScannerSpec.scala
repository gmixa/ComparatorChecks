package de.mixas.comparatorchecks.reflections

import de.mixas.comparatorchecks.refelections.Scanner
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ScannerSpec extends AnyFlatSpec with should.Matchers{

  "A Scanner" should "throw IllegalArgumentException if null is set as param type" in {
    a[IllegalArgumentException] should be thrownBy{
      Scanner(null.asInstanceOf[String])
    }
  }

  it should "find one class implementing Compareable in the package 'de' " in {
    val scanner = Scanner("tests")
    val found = scanner.allComparable()
    found.size should be (2)
  }

  it should "find the classes implementing Comparator in the package 'de' " in {
    val scanner = Scanner("tests")
    val found = scanner.allComparators()
    found.size should be (3)
  }

  it should "find all methods returning a Comparator in the package 'de' " in {
    val scanner = Scanner("tests")
    val found = scanner.allFieldsDefiningComparators()
    found.size should be (3)
  }

}
