package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.rng.Seed
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
class IntegerSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[java.lang.Integer] = Arbitrary(
    Gen.chooseNum(Int.MinValue,Int.MaxValue).map(java.lang.Integer.valueOf)
  )
  given Cogen[java.lang.Integer] = Cogen(_.toInt)
  import scala.math.Ordering.*
  val integerDefaultOrder: Ordering[java.lang.Integer] = ordered[java.lang.Integer]
  given Order[java.lang.Integer] = Order.fromOrdering(integerDefaultOrder)
  checkAll("java.lang.Integer",OrderTests[java.lang.Integer].order)