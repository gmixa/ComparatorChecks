package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class EnumSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[Color] = Arbitrary(
    Gen.oneOf(Seq(Color.RED, Color.BLUE, Color.GREEN))
  )

  val mapped: Color => Long = { c =>
    c match
      case Color.RED => 1
      case Color.BLUE => 2
      case Color.GREEN => 3
  }

  given Cogen[Color] = Cogen(mapped)

  import scala.math.Ordered.*
  import scala.math.Ordering.*

  given cmp: AsComparable[Color] = c => c.compareTo

  val enumOrder: Ordering[Color] = ordered[Color]

  given Order[Color] = Order.fromOrdering(enumOrder)

  checkAll("java.lang.Enum", OrderTests[Color].order)
