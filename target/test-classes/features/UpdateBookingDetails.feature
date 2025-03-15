Feature: To Update current Booking Details

Background: 
Given user has access to endpoint "/auth"
When user makes a request with valid username and password
Then user should get the response code 200
And token should present in response


@updateDetailsByPUT
Scenario: To update Checkout date and Totalprice with complete payload
Given user has access to endpoint "/booking"
When user makes a request with complete payload
Then user should get the response code 200
And response should contain updated details



#@updateDetailsByPATCH
#Scenario: To update Checkout date and Totalprice with partial payload
##When user makes a request with partial payload
##And response should contain updated details




