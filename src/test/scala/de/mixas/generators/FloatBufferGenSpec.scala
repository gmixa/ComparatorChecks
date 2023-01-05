package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.FloatBuffer

class FloatBufferGenSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks:

  val hasPositiveSize: FloatBuffer => Assertion = b => b.capacity() should be > 0

  behavior of "FloatBufferGen"

  it should "have arrays of size >0 " in forAll(FloatBufferGen.floatbuffer)(hasPositiveSize)
