package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.LongBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class LongBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.LongBuffer] = Arbitrary(
    LongBufferGen.longbuffer
  )

  given Cogen[java.nio.LongBuffer] = Cogen(_.capacity())

  import scala.math.Ordering.*

  given Ordering[java.nio.LongBuffer] = ordered[java.nio.LongBuffer]

  given Order[java.nio.LongBuffer] = Order.fromOrdering

  checkAll("java.nio.LongBuffer", OrderTests[java.nio.LongBuffer].order)
end LongBufferSpec
