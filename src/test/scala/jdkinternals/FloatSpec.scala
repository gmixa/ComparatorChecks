package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class FloatSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.Float] = Arbitrary(
    Gen.chooseNum(Float.MinValue, Float.MaxValue).map(java.lang.Float.valueOf)
  )

  given Cogen[java.lang.Float] = Cogen(_.toLong)

  import scala.math.Ordering.*

  val floatDefaultOrder: Ordering[java.lang.Float] = ordered[java.lang.Float]

  given Order[java.lang.Float] = Order.fromOrdering(floatDefaultOrder)

  checkAll("java.lang.Float", OrderTests[java.lang.Float].order)
