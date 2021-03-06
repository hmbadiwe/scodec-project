package com.example.protocol.codec

import com.example.protocol.codec.CodecHelper.CodecOptionable
import scodec.codecs._
import scodec.{Err, Codec}
import scodec.bits._

import scalaz._
import Scalaz._
import scalaz.\/

/**
 * Created by hmbadiwe on 11/20/14.
 */
class OptionCodec[ T : CodecOptionable] extends Codec[Option[T]]{
  override def encode( value: Option[T] ): \/[ Err, BitVector ] = {
    value match{
       case Some( encodeAble ) => implicitly[CodecOptionable[T]].codec.encode(encodeAble)
       case _ => \/.right( BitVector(implicitly[CodecOptionable[T]].invalid))
     }
  }

  override def decode( bits: BitVector ): \/[ Err, (BitVector, Option[T]) ] = {
    try{
      val size = implicitly[CodecOptionable[T]].size
      val invalidBits = implicitly[CodecOptionable[T]].invalid.bits
      val payload = bits.take(size)
      val remainingBits = bits.drop(size)
      if( payload == invalidBits) ( remainingBits, None ).right
      else implicitly[CodecOptionable[T]].codec.decode(bits).map{ d => (remainingBits, Option(d._2))}
    }
    catch {
      case _ : Throwable => Err( s"Trouble decoding option string from '0x${bits.toByteVector.toHex}" ).left
    }

  }
}
