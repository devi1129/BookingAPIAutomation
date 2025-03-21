Feature: Create a new booking

  @createBookingDataTable
 Scenario Outline: To create a new booking using cucumber Data Table
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createBookingSchema.json"

  Examples:
    | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
    | John      | Doe      | 1200       | true        | 2021-05-05 | 2021-05-15 | Breakfast       |
    | Jane      | Doe      | 2400       | false       | 2021-06-01 | 2021-07-10 | Dinner          |

    
 @createBookingFromExcel
Scenario Outline: To create new booking using Excel data
 
  Given user has access to endpoint "/booking"
When user creates a booking using "<dataKey>" from sheet "testData"
Then user should get the response code 200
And user validates the response schema from excelcolumn "responseSchema"

Examples:

|dataKey|
|createBooking1|
|createBooking2|

@createBookingFromJSON
Scenario Outline: To create new Booking using json data
  Given user has access to endpoint "/booking"
When user creates a Booking from json file "<filename>" and the key "<key>"
Then user should get the response code 200
And user validates the response with JSON schema "createBookingSchema.json"

Examples:
|filename|key|
|createBookingbody.json| createBooking1|
|createBookingbody.json| createBooking2|
 


