package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.rng.Seed
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.lang.Boolean

class BooleanSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:

  given Arbitrary[java.lang.Boolean] = Arbitrary(
    Gen.oneOf(java.lang.Boolean.TRUE,java.lang.Boolean.FALSE)
  )

  given Cogen[java.lang.Boolean] = Cogen( b => if b then 1L else 0L)

  import scala.math.Ordering.*

  val booleanDefaultOrder: Ordering[java.lang.Boolean] = ordered[java.lang.Boolean]

  given catsBooleanClassOrder : Order[java.lang.Boolean] = Order.fromOrdering(booleanDefaultOrder)

  checkAll("java.lang.Boolean",OrderTests[java.lang.Boolean].order)