package com.example

import org.joda.time.LocalDate
import org.scalatest.{ShouldMatchers, FlatSpec}
import com.example.protocol.codec.CodecHelper.CodecOptionable._

/**
 * Created by hmbadiwe on 11/19/14.
 */
class TestCodecable extends FlatSpec with ShouldMatchers with CodecTestHelper{
  behavior of "Custom Codecs"

  it should "properly encode and decode a LocalDate" in {
    encodeDecodeValue( new LocalDate(2012, 12, 23))
  }
  it should "properly encode and decode a character" in {
    encodeDecodeValue( 'Q' )
  }
  
}
