Scodec Encoding/Decoding Project
================================

Objectives
-------------------------------

The scodec library is an open source project that was written by one of CCAD's developers.
We utilize scodec in various products. Take a look at the scodec library on github at: https://github.com/scodec/scodec.

For performance reasons, a new HR system requires binary encoding of the Employee data type.
Design a binary protocol and use the Scodec project to write an encoder and decoder for the data type below. 
Strings can be of arbitrary length. Use ScalaTest, http://www.scalatest.org/, to test your code.

case class Employee(
  firstName: String,
  middleInitial: Option[Char],
  lastName: String,
  dateOfBirth: Date,
  startDate: Date,
  terminationDate: Option[Date],
  rank: Int
)

The focus for this exercise is not a complete running application. We are looking for how you would solve this problem using SCODEC and Scalatest. Use whatever Development Environment and other support tools you are comfortable with.

Results
--------------------
The Employee codec needed support from some auxilliary codecs I created.

These are the three codecs that were created to support this effort :

 * CharCodec - for decoding Char(s)
 * LocalDateCodec - for decoding Jodatime dates
 * OptionCodec - A codec that uses type classes/implicits for handling each of the above. Two companion objects were created :
    * OptionalCharCodec - For encoding/decoding optional chars
    * OptionalDateCodec - For encoding/decoding optional dates

 This exercise is a lot easier for items of a fixed width.


The Employee codec has a companion object/singleton that contains a codec tuple for each of the types.
UTF-8 encoding was used for encoding/decoding strings. The fixed width codecs were specified first in the codec tuple.

```
 (  OptionalCharCodec  ~ LocalDateCodec ~ LocalDateCodec ~ OptionalDateCodec ~ int32L ~ utf8 )
```




