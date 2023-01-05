package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class ShortSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.Short] = Arbitrary(
    Gen.chooseNum(Short.MinValue, Short.MaxValue).map(java.lang.Short.valueOf)
  )

  given Cogen[java.lang.Short] = Cogen(_.toShort)

  import scala.math.Ordering.*

  val shortDefaultOrder: Ordering[java.lang.Short] = ordered[java.lang.Short]

  given Order[java.lang.Short] = Order.fromOrdering(shortDefaultOrder)

  checkAll("java.lang.Short", OrderTests[java.lang.Short].order)
