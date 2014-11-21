package com.example.protocol.codec

import com.example.domain.Employee
import com.example.protocol.codec.CodecHelper.{OptionalDateCodec, OptionalCharCodec}
import org.joda.time.LocalDate
import scodec.{Err, Codec}
import scodec.codecs._
import scodec.bits.BitVector

import scalaz._
import Scalaz._

/**
 * Created by hmbadiwe on 11/20/14.
 */
class EmployeeCodec extends Codec[Employee] {
  import EmployeeCodec._

  override def encode( value: Employee ): \/[ Err, BitVector ] = {
    codec.encode(((((value.middleInitial,value.dateOfBirth),value.startDate),value.terminationDate),value.rank),value.firstName + ":" + value.lastName)
  }

  override def decode( bits: BitVector ): \/[ Err, (BitVector, Employee) ] = {
    val decodedValue = codec.decode(bits)
    val \/-(( buffer, (((((middleInitial, birthDate), startDate), terminationOptDate), rank), name))) = decodedValue
    val names = name.split(':')
    val firstName = names(0)
    val lastName = names(1)

    val returnedEmployee = Employee( firstName, middleInitial, lastName, birthDate, startDate, terminationOptDate, rank)
    (buffer, returnedEmployee).right
  }
}

object EmployeeCodec extends EmployeeCodec{

    private val codec =
        (  OptionalCharCodec  ~ LocalDateCodec ~ LocalDateCodec ~ OptionalDateCodec ~ int32L ~ utf8 )
}
