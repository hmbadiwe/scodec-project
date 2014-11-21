package com.example.protocol.codec

import com.example.domain.Employee
import org.joda.time.LocalDate
import scodec.Err.General
import scodec.{Codec, Err}
import scodec.bits._

import scodec.codecs._
import scalaz.{Semigroup, \/}


/**
 * Created by hmbadiwe on 11/19/14.
 */
object CodecHelper {
  trait CodecOptionable[T] {
    def codec : Codec[T] with ExplicitlyDefinedBits
    def size : Long = codec.numberOfBits
    val invalid : ByteVector

  }

  object CodecOptionable {
    implicit object CodecOptionableChar extends CodecOptionable[Char]{
      def codec = charCodec
      val invalid = hex"F1FA"
      val charCodec = new CharCodec

    }
    implicit object CodecOptionableDate extends CodecOptionable[LocalDate]{
      def codec = dateCodec
      val invalid = hex"F1FAFADE"
      val dateCodec = new LocalDateCodec
    }

  }

  object OptionalDateCodec extends OptionCodec[LocalDate]
  object OptionalCharCodec extends OptionCodec[Char]

  implicit object BitVectorSemigroup extends Semigroup[BitVector]{
    override def append( f1: BitVector, f2: => BitVector ): BitVector = {
      f1 ++ f2
    }
  }
  implicit object ErrSemigroup extends Semigroup[Err]{
    override def append( f1: Err, f2: => Err ): Err = General( f1.message + ", " + f2.message, f1.context ++ f2.context)
  }

}
