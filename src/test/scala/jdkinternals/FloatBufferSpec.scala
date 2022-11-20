package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.FloatBufferGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class FloatBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :

  given Arbitrary[java.nio.FloatBuffer] = Arbitrary(
    FloatBufferGen.floatbuffer
  )

  given Cogen[java.nio.FloatBuffer] = Cogen(_.capacity())

  import scala.math.Ordering.*

  given Ordering[java.nio.FloatBuffer] = ordered[java.nio.FloatBuffer]

  given Order[java.nio.FloatBuffer] = Order.fromOrdering

  checkAll("java.nio.FloatBuffer", OrderTests[java.nio.FloatBuffer].order)
end FloatBufferSpec
