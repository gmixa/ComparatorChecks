package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.LocalTime

class LocalTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[LocalTime] = Arbitrary(
    Gen.chooseNum[Long](0, 86399).map(LocalTime.ofSecondOfDay)
  )

  given Cogen[LocalTime] = Cogen(_.toSecondOfDay)

  import scala.math.Ordering.*

  val defaultOrder: Ordering[LocalTime] = ordered[LocalTime]

  given Order[LocalTime] = Order.fromOrdering(defaultOrder)

  checkAll("java.time.LocalTime", OrderTests[java.time.LocalTime].order)
end LocalTimeSpec
