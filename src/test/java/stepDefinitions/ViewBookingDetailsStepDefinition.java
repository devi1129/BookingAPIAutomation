package stepDefinitions;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;

import Utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.BookingIdsPojo;
import model.CreateBookingDTO;
import model.ViewBookingDetailsDTO;

public class ViewBookingDetailsStepDefinition {
	private static final Logger LOG = LogManager.getLogger(ViewBookingDetailsStepDefinition.class);


	TestContext testContext;

	public ViewBookingDetailsStepDefinition(TestContext testContext) {
		this.testContext = testContext;
	}
	
	@When("user makes a request for the created bookingID")
	public void user_makes_a_request_for_the_created_booking_id() {
		System.out.println(testContext.session.get("endpoint").toString());
		System.out.println(testContext.session.get("BookingID").toString());
	testContext.response=	testContext.req.get(testContext.session.get("endpoint").toString()+"/"+Integer.parseInt(testContext.session.get("BookingID").toString()));
	
	
	 ViewBookingDetailsDTO responseString = testContext.response.as(ViewBookingDetailsDTO.class);
	    
	    // Log the raw response to see what is returned
	    

	        // Parse the response string into a JSONObject
	       
	        testContext.session.put("Actual Response", (ViewBookingDetailsDTO)responseString);
	  
	}
	public void validateResponse(CreateBookingDTO bookingData, ViewBookingDetailsDTO bookingResponse) {
		Assert.assertEquals(bookingResponse.getFirstname(), bookingData.getBooking().getFirstname());
		Assert.assertEquals(bookingResponse.getLastname(), bookingData.getBooking().getLastname());
		Assert.assertEquals(bookingResponse.getTotalprice(),bookingData.getBooking().getTotalprice());
		Assert.assertEquals(bookingResponse.isDepositpaid(), bookingData.getBooking().getDepositpaid());
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckin(), bookingData.getBooking().getBookingdates().getCheckin());
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckout(), bookingData.getBooking().getBookingdates().getCheckout());
		Assert.assertEquals(bookingResponse.getAdditionalneeds(), bookingData.getBooking().getAdditionalneeds());

		LOG.info("Successfully validated the response body");
	}

	
	@Then("user should be able to view respective booking")
	public void user_should_be_able_to_view_respective_booking_details() {
	    // Get the response as a string
	   
	        // Validate the actual response against the expected response stored in the session
	        validateResponse((CreateBookingDTO)testContext.session.get("Expected Response"),(ViewBookingDetailsDTO)testContext.session.get("Actual Response"));
	    
	      
	        LOG.info("Successfully viewed correct details");

	    }
	
	List<BookingIdsPojo> bookings;
	
	@When("user makes a request to view all IDs")
	public void user_makes_a_request_to_view_all_i_ds() {
		
		testContext.response=	testContext.req.get(testContext.session.get("endpoint").toString());	
		  bookings = testContext.response.jsonPath().getList("$", BookingIdsPojo.class);
		  testContext.session.put("BookingID",bookings.get(0).getBookingid());
	  
	}
	@Then("user should be able to view all id's")
	public void user_should_be_able_to_view_all_id_s() {
		
//		for (BookingIdsPojo booking : bookings) {
//            System.out.println("Booking ID: " + booking.getBookingid());
//        }
	  
	}
	@Then("user should be able to view details of any ID")
	public void user_should_be_able_to_view_details_of_any_id() {
		
		
		
		user_makes_a_request_for_the_created_booking_id();
		 String responseString = testContext.response.asString();
		 System.out.println("Details from one of the id---->"+bookings.get(0).getBookingid());
		 System.out.println(responseString);
	 
	}
	
	@When("user makes a request")
	public void user_makes_a_request() {
	testContext.response=	testContext.req.get(testContext.session.get("endpoint").toString());
		
	}

}


