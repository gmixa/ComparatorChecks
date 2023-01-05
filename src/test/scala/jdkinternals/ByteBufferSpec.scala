package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.ByteBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.nio.ByteBuffer

class ByteBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.ByteBuffer] = Arbitrary(
    ByteBufferGen.bytebuffer
  )

  given Cogen[java.nio.ByteBuffer] = Cogen(_.capacity())

  import scala.math.Ordering.*

  val byteBufferDefaultOrder: Ordering[ByteBuffer] = ordered[java.nio.ByteBuffer]

  given Order[java.nio.ByteBuffer] = Order.fromOrdering(byteBufferDefaultOrder)

  checkAll("java.nio.ByteBuffer", OrderTests[java.nio.ByteBuffer].order)
end ByteBufferSpec
