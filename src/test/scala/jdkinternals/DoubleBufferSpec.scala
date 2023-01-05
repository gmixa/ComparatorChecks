package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.DoubleBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class DoubleBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.DoubleBuffer] = Arbitrary(
    DoubleBufferGen.doublebuffer
  )

  given Cogen[java.nio.DoubleBuffer] = Cogen(_.capacity())

  import scala.math.Ordering.*

  given Ordering[java.nio.DoubleBuffer] = ordered[java.nio.DoubleBuffer]

  given Order[java.nio.DoubleBuffer] = Order.fromOrdering

  checkAll("java.nio.DoubleBuffer", OrderTests[java.nio.DoubleBuffer].order)
end DoubleBufferSpec
