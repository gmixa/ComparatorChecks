package de.mixas.generators

import org.scalacheck.Gen
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{LongBuffer, ShortBuffer}

object ShortBufferGen:
  val shortbuffer: Gen[ShortBuffer] = Gen.containerOf[Array, Short](Gen.chooseNum(Short.MinValue, Short.MaxValue))
    .map(f => ShortBuffer.wrap(f)).suchThat(f => f.capacity > 0)
end ShortBufferGen


