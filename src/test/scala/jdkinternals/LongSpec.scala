package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class LongSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :

  given Arbitrary[java.lang.Long] = Arbitrary(
    Gen.chooseNum(scala.Long.MinValue, scala.Long.MaxValue).map(java.lang.Long.valueOf)
  )

  given Cogen[java.lang.Long] = Cogen(_.toLong)

  import scala.math.Ordering.*

  val longDefaultOrder: Ordering[java.lang.Long] = ordered[java.lang.Long]

  given Order[java.lang.Long] = Order.fromOrdering(longDefaultOrder)

  checkAll("java.lang.Long", OrderTests[java.lang.Long].order)
