package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.temporal.ChronoField
import java.time.{LocalTime, OffsetTime, ZoneOffset}

class OffsetTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
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
      ZoneOffset.ofHoursMinutesSeconds(-hours, -minutes, -seconds)
    end if
  end zoneOffset
  given Arbitrary[OffsetTime] = Arbitrary(for
    lt <- localTime
    zd <- zoneOffset
  yield
    val offsetSeconds = zd.get(ChronoField.OFFSET_SECONDS)
    if lt.getHour < 0 &&  offsetSeconds> 0  then
      val newZD = ZoneOffset.ofTotalSeconds(-offsetSeconds)
      OffsetTime.of(lt, newZD)
    else
      OffsetTime.of(lt,zd)
  )

  given Cogen[OffsetTime] = Cogen(_.toLocalTime.toNanoOfDay)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[OffsetTime] = ordered[OffsetTime]

  given Order[OffsetTime] = Order fromOrdering defaultOrder

  checkAll("java.time.OffsetTime", OrderTests[OffsetTime].order)

end OffsetTimeSpec
