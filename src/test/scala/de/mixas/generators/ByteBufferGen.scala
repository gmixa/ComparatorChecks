package de.mixas.generators

import java.nio.ByteBuffer
import org.scalacheck.Gen
object ByteBufferGen {

  val bytebuffer : Gen[ByteBuffer] = Gen.containerOf[Array,Byte](Gen.chooseNum(Byte.MinValue,Byte.MaxValue)).map( d => ByteBuffer.wrap(d)).filter(p => p.array().size>0)
  
}
