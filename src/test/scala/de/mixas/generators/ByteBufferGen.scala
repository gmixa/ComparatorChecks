package de.mixas.generators

import org.scalacheck.Gen

import java.nio.ByteBuffer

object ByteBufferGen {

  val bytebuffer: Gen[ByteBuffer] = Gen.containerOf[Array, Byte](Gen.chooseNum(Byte.MinValue, Byte.MaxValue)).
    map(d => ByteBuffer.wrap(d)).suchThat(p => p.array().length > 0)
}
