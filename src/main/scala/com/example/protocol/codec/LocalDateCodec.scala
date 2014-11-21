package com.example.protocol.codec

import org.joda.time.LocalDate
import scodec._
import scodec.bits._
import codecs._

import scalaz.\/

/**
 * Created by hmbadiwe on 11/19/14.
 */
class LocalDateCodec extends Codec[LocalDate] with ExplicitlyDefinedBits{
  val numberOfBits = 32l
  val aggregateIncCodec = ( uint16 ~ uint8 ~ uint8 )
  override def encode( value: LocalDate ): \/[ Err, BitVector ] = {
    val result = aggregateIncCodec.encode( ((value.getYear, value.getMonthOfYear), value.getDayOfMonth) )
    result
  }

  override def decode( bits: BitVector ): \/[ Err, (BitVector, LocalDate) ] = {
    val decodedResult = aggregateIncCodec.decode( bits.take(numberOfBits) )
    val result = decodedResult.map{ eb =>
    (bits.drop(numberOfBits), new LocalDate(eb._2._1._1, eb._2._1._2, eb._2._2))
    }
    result
  }
}

object LocalDateCodec extends LocalDateCodec
