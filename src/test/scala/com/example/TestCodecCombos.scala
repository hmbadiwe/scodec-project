package com.example

import com.example.protocol.codec.CodecHelper._
import org.joda.time.LocalDate
import org.scalatest.{ShouldMatchers, FlatSpec}
import scodec.Codec
import scodec.bits.BitVector

import scalaz.\/-

/**
 * Created by hmbadiwe on 11/21/14.
 */

class TestCodecCombos extends FlatSpec with ShouldMatchers{

  val charOptThenDateOptCodec = ( OptionalCharCodec ~ OptionalDateCodec )
  val dateOptThenCharOptCodec = ( OptionalDateCodec ~ OptionalCharCodec )

   it should "decode combos of options in case classes" in {
     val charOptEnc = OptionalCharCodec.encode( Option('K') )
     val dateOptEnc = OptionalDateCodec.encode( Option( new LocalDate(2011,10,10)))

     val charOptDateOptEnc = for{
       charOpt <- charOptEnc
       dateOpt <- dateOptEnc
     } yield( charOpt ++ dateOpt )


     val \/-((buffer, (charOptResult, dateOptResult))) = charOptThenDateOptCodec.decode( charOptDateOptEnc.toOption.get)

     buffer shouldBe BitVector.empty
     charOptResult shouldBe Some('K')
     dateOptResult shouldBe Some(new LocalDate(2011,10,10))


   }
   it should "decode a case where the char option is None and the date option isn't" in {
     val \/-((buffer, (charOptResult, dateOptResult))) = charOptThenDateOptCodec.decode( charOptThenDateOptCodec.encode((None, Option(new LocalDate(2014,11,11)))).toOption.get )

     buffer shouldBe BitVector.empty
     charOptResult shouldBe None
     dateOptResult shouldBe Some(new LocalDate(2014,11,11))


   }
  it should "decode a case where the date option is None and the char option isn't" in {
    val \/-((buffer, (charOptResult, dateOptResult))) = charOptThenDateOptCodec.decode( charOptThenDateOptCodec.encode((Option('z'), None)).toOption.get )

    buffer shouldBe BitVector.empty
    charOptResult shouldBe Some('z')
    dateOptResult shouldBe None
  }
   "For curiosity's sake, my implicits" should "be able to concatenate bit vectors. At least on success" in {
     val charOptEnc = OptionalCharCodec.encode( Option('K') )
     val dateOptEnc = OptionalDateCodec.encode( Option( new LocalDate(2011,10,10)))

     val dateOptCharOptEnc = dateOptEnc +++ charOptEnc

     val \/-((buffer, ( dateOptResult, charOptResult ))) = dateOptThenCharOptCodec.decode( dateOptCharOptEnc.toOption.get)

     dateOptResult shouldBe Some(new LocalDate(2011,10,10))
     charOptResult shouldBe Some('K')


  }
}
