package de.mixas.generators

import org.scalacheck.Gen
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{DoubleBuffer, FloatBuffer}

object FloatBufferGen:
  val floatbuffer: Gen[FloatBuffer] = Gen.containerOf[Array, Float](Gen.chooseNum(Float.MinValue, Float.MaxValue)).map(f => FloatBuffer.wrap(f))
    .suchThat(f => f.capacity() > 0)
end FloatBufferGen


