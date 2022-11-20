package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.IntBuffer

class IntBufferGenSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks :

  val hasPositiveSize: IntBuffer => Assertion = b => b.capacity() should be > 0

  behavior of "IntBufferGen"

  it should "have arrays of size >0 " in forAll(IntBufferGen.intbuffer)(hasPositiveSize)
