package com.example

import com.example.protocol.codec.{OptionCodec}
import org.joda.time.LocalDate
import org.scalatest.{ShouldMatchers, FlatSpec}
import scodec.bits.BitVector
import scodec.codecs._

import scala.Some
import scalaz.\/-

/**
 * Created by hmbadiwe on 11/19/14.
 */
class TestOptionCodec extends FlatSpec with ShouldMatchers with CodecTestHelper {
     behavior of "Option Codec"

    it should "properly encode and decode an optional date" in {
      val testDate = new LocalDate(2015, 11,12)
      encodeDecodeOptionValue( Option(testDate) )
    }
    it should "properly encode and decode None" in {
      val optionChar : Option[Char] = None
      encodeDecodeOptionValue(optionChar)
      val optionDate : Option[LocalDate] = None
      encodeDecodeOptionValue(optionDate)

    }
}
