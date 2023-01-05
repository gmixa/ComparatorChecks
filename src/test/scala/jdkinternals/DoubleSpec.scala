package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.lang

class DoubleSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.Double] = Arbitrary(
    Gen.chooseNum(Double.MinValue, Double.MaxValue).map(java.lang.Double.valueOf)
  )

  given Cogen[java.lang.Double] = Cogen(_.toLong)

  import scala.math.Ordering.*

  val doubleDefaultOrder: Ordering[lang.Double] = ordered[java.lang.Double]

  given Order[java.lang.Double] = Order.fromOrdering(doubleDefaultOrder)

  checkAll("java.lang.Double", OrderTests[java.lang.Double].order)
