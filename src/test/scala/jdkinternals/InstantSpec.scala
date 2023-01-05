package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.Instant

class InstantSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[Instant] = Arbitrary(
    Gen.chooseNum(Long.MinValue, Long.MaxValue).map(u => Instant.ofEpochMilli(u))
  )

  given Cogen[Instant] = Cogen(_.toEpochMilli)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[Instant] = ordered[Instant]

  given Order[Instant] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.Instant", OrderTests[Instant].order)

end InstantSpec
