package com.example

import com.example.protocol.codec.OptionCodec
import org.scalatest.ShouldMatchers
import scodec.bits.BitVector

import scalaz.\/-

/**
 * Created by hmbadiwe on 11/19/14.
 */
trait CodecTestHelper {
  self : ShouldMatchers =>
  import com.example.protocol.codec.CodecHelper._

  def encodeDecodeValue[T]( x : T )(implicit c : CodecOptionable[T]) = {
    val encodedValue = c.codec.encode( x )
    encodedValue.isRight shouldBe true
    encodedValue.foreach {  value  =>
      val \/-( (buffer, decodedValue) )  = c.codec.decode( value )
      decodedValue shouldBe x
    }
  }

  def encodeDecodeOptionValue[T]( x : Option[T] )(implicit c : CodecOptionable[T]) = {
    val optionCodec = new OptionCodec[T]
    val encodedValue = optionCodec.encode( x )
    encodedValue.isRight shouldBe true
    val \/-( (_, decodedValue) )  = optionCodec.decode( encodedValue.toOption.get)
    decodedValue shouldBe x
  }

}
