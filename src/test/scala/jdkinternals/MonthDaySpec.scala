package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.time.MonthDay
import java.time.temporal.ChronoField

class MonthDaySpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  //TODO: Leap Year Generation
  given Arbitrary[java.time.MonthDay] = Arbitrary({
    val months: Gen[Int] = Gen.chooseNum[Int](1, 12)
    val days31: Gen[Int] = Gen.chooseNum(1, 31)
    val days30: Gen[Int] = Gen.chooseNum(1, 30)
    val days28: Gen[Int] = Gen.chooseNum(1, 28)
    val gen: Gen[MonthDay] = months.flatMap { month =>
      val g = month match
        case 1 => days31
        case 2 => days28
        case 3 => days31
        case 4 => days30
        case 5 => days31
        case 6 => days30
        case 7 => days31
        case 8 => days31
        case 9 => days30
        case 10 => days31
        case 11 => days30
        case 12 => days31
      g.map(d => MonthDay.of(month, d))
    }
    gen
  }
  )
  given Cogen[java.time.MonthDay] = Cogen(_.getLong(ChronoField.DAY_OF_YEAR))
  import scala.math.Ordering.*
  val defaultOrder: Ordering[MonthDay] = ordered[MonthDay]
  given Order[java.time.MonthDay] = Order.fromOrdering(defaultOrder)
  checkAll("java.time.MonthDay", OrderTests[MonthDay].order)
end MonthDaySpec
