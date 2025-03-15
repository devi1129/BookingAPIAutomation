Feature: View Booking Details

@ViewBookingDetailsByID
Scenario: To view Booking details for the created Booking
Given user has access to endpoint "/booking"
When user makes a request for the created bookingID
Then user should get the response code 200
And user should be able to view respective booking 



@ViewAllBookingID's
Scenario: To view all the Booking ID's
Given user has access to endpoint "/booking"
When user makes a request to view all IDs
Then user should get the response code 200
And user should be able to view all id's 
And user should be able to view details of any ID

@CheckAPIHealth
Scenario: To health check
Given user has access to endpoint "/ping"
When user makes a request 
Then user should get the response code 201