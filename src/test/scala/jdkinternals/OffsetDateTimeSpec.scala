package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.chrono.IsoChronology
import java.time.{LocalDate, LocalTime, OffsetDateTime, ZoneOffset}

class OffsetDateTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:

  import java.time.Year

  val isLeapYear: Int => Boolean = year => IsoChronology.INSTANCE.isLeapYear(year)
  val years: Gen[Int] = Gen.chooseNum(Year.MIN_VALUE, Year.MAX_VALUE)
  val days: Gen[Int] = Gen.chooseNum(1, 365)
  val leapDays: Gen[Int] = Gen.chooseNum(1, 366)
  val localDate: Gen[LocalDate] = years flatMap { year =>
    val usedDays =
      if isLeapYear(year) then
        leapDays else
        days
      end if
    usedDays map { day => LocalDate.ofYearDay(year, day) }
  }
  val localTime: Gen[LocalTime] = Gen.chooseNum(0, 86399)
    .map(LocalTime.ofSecondOfDay(_))
  val zoneOffset: Gen[ZoneOffset] = for
    posneg <- Gen.oneOf(-1,1)
    hours <- Gen.chooseNum(0, 17)
    minutes <- Gen.chooseNum(0, 59)
    seconds <- Gen.chooseNum(0, 59)
  yield
    if posneg == 1 then
      ZoneOffset.ofHoursMinutesSeconds(hours, minutes, seconds)
    else
      ZoneOffset.ofHoursMinutesSeconds(-hours,-minutes,-seconds)
    end if

  given Arbitrary[OffsetDateTime] = Arbitrary(for
    ld <- localDate
    lt <- localTime
    zd <- zoneOffset
  yield OffsetDateTime.of(ld, lt, zd)
  )

  given Cogen[OffsetDateTime] = Cogen(_.toEpochSecond)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[OffsetDateTime] = ordered[OffsetDateTime]

  given Order[OffsetDateTime] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.OffsetDateTime", OrderTests[OffsetDateTime].order)
end OffsetDateTimeSpec