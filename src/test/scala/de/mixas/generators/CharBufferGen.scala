package de.mixas.generators

import org.scalacheck.Gen

import java.nio.CharBuffer

object CharBufferGen {

  val charbuffer: Gen[CharBuffer] = Gen.containerOf[Array, Char](Gen.chooseNum(Char.MinValue, Char.MaxValue)).
    map(b => CharBuffer.wrap(b)).suchThat(c => c.capacity > 0)

}
