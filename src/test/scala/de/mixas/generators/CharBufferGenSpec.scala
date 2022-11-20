package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{ByteBuffer, CharBuffer}

class CharBufferGenSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks:

  val hasPositiveSize : CharBuffer => Assertion = b => b.capacity() should be > 0

  behavior of "CharBufferGen"

  it should "have arrays of size >0 " in forAll(CharBufferGen.charbuffer)(hasPositiveSize)
