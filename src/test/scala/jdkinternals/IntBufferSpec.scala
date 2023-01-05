package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.IntBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class IntBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.IntBuffer] = Arbitrary(
    IntBufferGen.intbuffer
  )

  given Cogen[java.nio.IntBuffer] = Cogen(_.capacity)

  import scala.math.Ordering.*

  given Ordering[java.nio.IntBuffer] = ordered[java.nio.IntBuffer]

  given Order[java.nio.IntBuffer] = Order.fromOrdering

  checkAll("java.nio.IntBuffer", OrderTests[java.nio.IntBuffer].order)
