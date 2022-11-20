package de.mixas.generators

import org.scalacheck.Gen
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{FloatBuffer, IntBuffer}

object IntBufferGen:
  val intbuffer : Gen[IntBuffer] = Gen.containerOf[Array,Int](Gen.chooseNum(Int.MinValue,Int.MaxValue))
    .map( u => IntBuffer.wrap(u)).suchThat(u => u.capacity() > 1)
end IntBufferGen



