package jdkinternals

import cats.Order
import org.scalacheck.rng.Seed
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import cats.kernel.laws.discipline.OrderTests
class IntegerSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given randomInt : Arbitrary[Integer] = Arbitrary(
    Gen.chooseNum(Int.MinValue,Int.MaxValue).map( i => Integer.valueOf(i))
  )
  given randomClazz : Cogen[Integer] = Cogen{ (seed : Seed, clazz : Integer) =>
    val seed1 = Cogen.perturb(seed,clazz)
    Cogen.perturb(seed1,clazz)
  }

  import scala.math.Ordering._

  val integerDefaultOrder : Ordering[Integer] = ordered[Integer]

  given catsIntegerClassorder : Order[Integer] = Order.fromOrdering(integerDefaultOrder)

  checkAll("java.lang.Integer",OrderTests[Integer].order)