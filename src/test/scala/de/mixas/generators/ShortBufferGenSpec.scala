package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.ShortBuffer

class ShortBufferGenSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks:
  val hasPositiveSize: ShortBuffer => Assertion = b => b.capacity() should be > 0

  behavior of "LongBufferGen"

  it should "have arrays of size >0 " in forAll(ShortBufferGen.shortbuffer)(hasPositiveSize)

end ShortBufferGenSpec
