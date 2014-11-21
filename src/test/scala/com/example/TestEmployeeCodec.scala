package com.example

import com.example.domain.Employee
import com.example.protocol.codec.EmployeeCodec

import org.joda.time.{LocalDate, DateTime}
import org.scalatest.{ShouldMatchers, FlatSpec}
import scodec.bits.BitVector

import scalaz.\/-

/**
 * Created by hmbadiwe on 11/18/14.
 */
class TestEmployeeCodec extends FlatSpec with ShouldMatchers {

  behavior of "Employee Encoder/decoder"

  val testEmployee = Employee( "Joe", Option('B'), "Schmoe", new LocalDate(1972, 10, 15 ), new LocalDate(2012, 6, 23 ), Option( new LocalDate(2013, 6,15 ) ), 5 )
  val testEmployeeStillWithUs = Employee( "Jane", Option('B'), "Doe", new LocalDate(1980, 5, 21 ), new LocalDate(2001, 9, 11 ), None, 11 )
  val testEmployeeNoMiddleName = Employee( "Suzie", None, "Summers", new LocalDate(1968, 3, 4 ), new LocalDate(1980, 12, 1 ), None, 11 )

  it should "properly encode/decode an employee" in {
    val \/-( (buffer, decodedValue) )  = EmployeeCodec.decode( EmployeeCodec.encode( testEmployee ).toOption.get )
    buffer shouldBe BitVector.empty
    decodedValue shouldBe testEmployee
  }

  it should "properly encode/decode an employee with no termination date" in {
    val \/-( (buffer, decodedValue) )  = EmployeeCodec.decode( EmployeeCodec.encode( testEmployeeStillWithUs ).toOption.get )
    buffer shouldBe BitVector.empty
    decodedValue shouldBe testEmployeeStillWithUs
  }
  it should "properly encode/decode an employee with no termination date and no middle name" in {
    val \/-( (buffer, decodedValue) )  = EmployeeCodec.decode( EmployeeCodec.encode( testEmployeeNoMiddleName ).toOption.get )
    buffer shouldBe BitVector.empty
    decodedValue shouldBe testEmployeeNoMiddleName
  }
}
