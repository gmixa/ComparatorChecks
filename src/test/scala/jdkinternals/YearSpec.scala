package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.Year

class YearSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[Year] = Arbitrary(Gen.chooseNum(Year.MIN_VALUE, Year.MAX_VALUE).map { year => Year.of(year) })

  given Cogen[Year] = Cogen(_.getValue)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[Year] = ordered[Year]

  given Order[Year] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.Year", OrderTests[Year].order)

end YearSpec
