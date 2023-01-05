package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class BigDecimalSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.math.BigDecimal] = Arbitrary(
    Gen.long.map(l => new java.math.BigDecimal(l))
  )

  given Cogen[java.math.BigDecimal] = Cogen(_.longValue())

  import scala.math.Ordering.*

  val bigDecimalDefaultOrder: Ordering[java.math.BigDecimal] = ordered[java.math.BigDecimal]

  given Order[java.math.BigDecimal] = Order.fromOrdering(bigDecimalDefaultOrder)

  checkAll("java.lang.Byte", OrderTests[java.math.BigDecimal].order)
