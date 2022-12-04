package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.LocalDate
import java.time.chrono.IsoChronology
import java.time.temporal.ChronoField

class LocalDateSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:

  import java.time.Year

  val isLeapYear: Int => Boolean = year => IsoChronology.INSTANCE.isLeapYear(year)
  val years: Gen[Int] = Gen.chooseNum(Year.MIN_VALUE, Year.MAX_VALUE)
  val days: Gen[Int] = Gen.chooseNum(1, 365)
  val leapDays: Gen[Int] = Gen.chooseNum(1, 366)
  val gen: Gen[LocalDate] = years flatMap { year =>
    val usedDays =
      if isLeapYear(year) then
        leapDays else
        days
      end if
    usedDays map { day => LocalDate.ofYearDay(year, day) }
  }

  given Arbitrary[LocalDate] = Arbitrary(gen)

  given Cogen[LocalDate] = Cogen(_.getLong(ChronoField.EPOCH_DAY))

  import scala.math.Ordering.*

  val defaultorder: Ordering[LocalDate] = ordered[LocalDate]

  given Order[LocalDate] = Order.fromOrdering(defaultorder)

  checkAll("java.time.LocalDate", OrderTests[LocalDate].order)
end LocalDateSpec
