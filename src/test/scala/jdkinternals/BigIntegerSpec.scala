package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.math.BigInteger

class BigIntegerSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.math.BigInteger] = Arbitrary(
    Gen.long.map(l => new BigInteger(String.valueOf(l)))
  )

  given Cogen[java.math.BigInteger] = Cogen(i => i.intValue())

  import scala.math.Ordering.*

  val bigIntDefaultOrder: Ordering[java.math.BigInteger] = ordered[java.math.BigInteger]

  given Order[java.math.BigInteger] = Order.fromOrdering(bigIntDefaultOrder)

  checkAll("java.math.BigInteger", OrderTests[java.math.BigInteger].order)

end BigIntegerSpec
