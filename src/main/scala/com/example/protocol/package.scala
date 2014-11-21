package com.example

import org.joda.time.{LocalDate, DateTime}

/**
 * Created by hmbadiwe on 11/18/14.
 */
package object domain {
  case class Employee( firstName: String
                       ,middleInitial: Option[Char]
                       ,lastName: String
                       ,dateOfBirth: LocalDate
                       ,startDate: LocalDate
                       ,terminationDate: Option[LocalDate]
                       ,rank: Int
                       )
}
