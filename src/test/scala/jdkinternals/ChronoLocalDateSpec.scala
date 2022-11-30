package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate

class ChronoLocalDateSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.time.chrono.ChronoLocalDate] = Arbitrary(
    Gen.choose(LocalDate.MIN, LocalDate.MAX))

  given Cogen[java.time.chrono.ChronoLocalDate] = Cogen(_.toEpochDay)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[ChronoLocalDate] = ordered[java.time.chrono.ChronoLocalDate]

  given Order[java.time.chrono.ChronoLocalDate] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.chrono.ChronoLocalDate", OrderTests[java.time.chrono.ChronoLocalDate].order)
end ChronoLocalDateSpec
