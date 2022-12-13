package de.mixas.generators

import org.scalacheck.Gen
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.{CharBuffer, DoubleBuffer}

object DoubleBufferGen:
  val doublebuffer: Gen[DoubleBuffer] = Gen.containerOf[Array, Double](Gen.chooseNum(Double.MinValue, Double.MaxValue))
    .map(d => DoubleBuffer.wrap(d)).suchThat(d => d.capacity > 0)