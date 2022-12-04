package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.{Year, YearMonth}

class YearMonthSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[YearMonth] = Arbitrary(
    for
      year <- Gen.chooseNum(Year.MAX_VALUE, Year.MAX_VALUE)
      month <- Gen.chooseNum(1, 12)
    yield YearMonth.of(year, month)
  )
  given Cogen[YearMonth] = Cogen(_.hashCode())
  import scala.math.Ordering.*
  val defaultOrder: Ordering[YearMonth] = ordered[YearMonth]
  given Order[YearMonth] = Order.fromOrdering(defaultOrder)
  checkAll("java.time.YearMonth", OrderTests[YearMonth].order)
end YearMonthSpec
