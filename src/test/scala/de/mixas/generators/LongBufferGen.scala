package de.mixas.generators

import org.scalacheck.Gen
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{IntBuffer, LongBuffer}

object LongBufferGen:
  val longbuffer : Gen[LongBuffer] = Gen.containerOf[Array,Long](Gen.chooseNum(Long.MinValue,Long.MaxValue))
    .map( u => LongBuffer.wrap(u)).suchThat(l => l.capacity() > 0)
end LongBufferGen



