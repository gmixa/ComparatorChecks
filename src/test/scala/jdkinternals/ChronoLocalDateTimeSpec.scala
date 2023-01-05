package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.{LocalDateTime, ZoneOffset}

class ChronoLocalDateTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.time.chrono.ChronoLocalDateTime[_]] = Arbitrary(
    Gen.choose(LocalDateTime.MIN, LocalDateTime.MAX))

  given Cogen[java.time.chrono.ChronoLocalDateTime[_]] = Cogen(_.toEpochSecond(ZoneOffset.UTC))

  import scala.math.Ordering.*

  val defaultOrder: Ordering[java.time.chrono.ChronoLocalDateTime[_]] = ordered[java.time.chrono.ChronoLocalDateTime[_]]

  given Order[java.time.chrono.ChronoLocalDateTime[_]] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.chrono.ChronoLocalDate", OrderTests[java.time.chrono.ChronoLocalDateTime[_]].order)
end ChronoLocalDateTimeSpec
