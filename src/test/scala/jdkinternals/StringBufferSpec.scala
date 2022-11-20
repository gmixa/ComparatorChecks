package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class StringBufferSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.lang.StringBuffer] = Arbitrary(
    Gen.alphaNumStr.map(u => new StringBuffer(u))
  )

  given Cogen[java.lang.StringBuffer] = Cogen(sb => sb.length())

  import scala.math.Ordering.*

  val byteDefaultOrder: Ordering[java.lang.StringBuffer] = ordered[java.lang.StringBuffer]

  given Order[java.lang.StringBuffer] = Order.fromOrdering(byteDefaultOrder)

  checkAll("java.lang.StringBuffer", OrderTests[java.lang.StringBuffer].order)
