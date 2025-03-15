Feature: To Delete booking

Background: 
Given user has access to endpoint "/auth"
When user makes a request with valid username and password
Then user should get the response code 200
And token should present in response


@DeleteBookingDetails
Scenario: To Delete booking details
Given user has access to endpoint "/booking"
When user makes a request to delete
Then user should get the response code 201
