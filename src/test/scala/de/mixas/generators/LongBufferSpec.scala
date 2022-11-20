package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.LongBuffer

class LongBufferSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks :

  val hasPositiveSize: LongBuffer => Assertion = b => b.capacity() should be > 0

  behavior of "LongBufferGen"

  it should "have arrays of size >0 " in forAll(LongBufferGen.longbuffer)(hasPositiveSize)

end LongBufferSpec
