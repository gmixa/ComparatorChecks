package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class CharacterSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.Character] = Arbitrary(
    Gen.chooseNum(Char.MinValue, Char.MaxValue).map(java.lang.Character.valueOf)
  )

  given Cogen[java.lang.Character] = Cogen(_.toChar)

  import scala.math.Ordering.*

  val characterDefaultOrder: Ordering[java.lang.Character] = ordered[java.lang.Character]

  given Order[java.lang.Character] = Order.fromOrdering(characterDefaultOrder)

  checkAll("java.lang.Character", OrderTests[java.lang.Character].order)
