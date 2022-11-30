package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.ShortBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class ShortBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.ShortBuffer] = Arbitrary(ShortBufferGen.shortbuffer)

  given Cogen[java.nio.ShortBuffer] = Cogen(_.capacity)

  import scala.math.Ordering.*

  given Ordering[java.nio.ShortBuffer] = ordered[java.nio.ShortBuffer]

  given Order[java.nio.ShortBuffer] = Order.fromOrdering

  checkAll("java.nio.ShortBuffer", OrderTests[java.nio.ShortBuffer].order)
