package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.CharBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class CharBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.nio.CharBuffer] = Arbitrary(
    CharBufferGen.charbuffer
  )

  given Cogen[java.nio.CharBuffer] = Cogen(_.capacity)

  import scala.math.Ordering.*

  given Ordering[java.nio.CharBuffer] = ordered[java.nio.CharBuffer]

  given Order[java.nio.CharBuffer] = Order.fromOrdering

  checkAll("java.nio.CharBuffer", OrderTests[java.nio.CharBuffer].order)

end CharBufferSpec
