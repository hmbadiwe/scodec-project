package com.example.protocol.codec

import scodec.{Err, Codec}
import scodec.codecs._
import scodec.bits.BitVector

import scalaz.\/

/**
 * Created by hmbadiwe on 11/20/14.
 */
class CharCodec extends Codec[Char] with ExplicitlyDefinedBits{

  val numberOfBits = 16l

  override def encode( value: Char ): \/[ Err, BitVector ] = {
     val res = uint16.encode(value.toInt)
    res
  }

  override def decode( bits: BitVector ): \/[ Err, (BitVector, Char) ] = {
    val result = uint16.decode(bits.take(numberOfBits)).map{ res =>
      (bits.drop(numberOfBits), res._2.toChar)
    }
    result
  }
}
