package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.Duration
import java.time.temporal.TemporalUnit
import scala.math.Ordering.ordered

class DurationSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:

  import java.time.temporal.ChronoUnit.*

  /*val arbitraryChronoUnit: Arbitrary[TemporalUnit] = Arbitrary(
    Gen.oneOf(Seq(NANOS, MICROS, MILLIS, SECONDS, MINUTES, HOURS, HALF_DAYS, DAYS, WEEKS, MONTHS,
      YEARS, DECADES, CENTURIES, MILLENNIA, ERAS, FOREVER))
  )*/
  val arbitraryChronoUnit : Arbitrary[TemporalUnit] = Arbitrary(Gen.const(MILLIS))

  val arbitraryAmount: Arbitrary[Long] = Arbitrary(
    Gen.chooseNum(Long.MinValue, Long.MaxValue)
  )

  import org.scalacheck.Gen.const

  given Arbitrary[Duration] = Arbitrary(
    for {
      amount: Long <- arbitraryAmount.arbitrary
      chronoUnit: TemporalUnit <- arbitraryChronoUnit.arbitrary
    } yield Duration.of(amount, chronoUnit)
  )

  given Cogen[Duration] = Cogen(_.toMillis)

  val defaultOrder: Ordering[Duration] = ordered[Duration]

  given Order[Duration] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.Duration", OrderTests[Duration].order)


end DurationSpec
