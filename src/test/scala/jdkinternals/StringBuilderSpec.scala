package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class StringBuilderSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.StringBuilder] = Arbitrary(
    Gen.alphaNumStr.map(s => new java.lang.StringBuilder(s))
  )

  given Cogen[java.lang.StringBuilder] = Cogen(s => s.length())

  import scala.math.Ordering.*

  val stringBuilderDefaultOrder: Ordering[java.lang.StringBuilder] = ordered[java.lang.StringBuilder]

  given Order[java.lang.StringBuilder] = Order.fromOrdering(stringBuilderDefaultOrder)

  checkAll("java.lang.StringBuilder", OrderTests[java.lang.StringBuilder].order)
