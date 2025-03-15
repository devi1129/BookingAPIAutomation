package stepDefinitions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;

import Utils.PropertiesFile;
import Utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import model.AuthDTO;
import model.CreateBookingDTO;
import model.ViewBookingDetailsDTO;

public class UpdateBookingDetailsStepDefinition {
	private static final Logger LOG = LogManager.getLogger(UpdateBookingDetailsStepDefinition.class);

	TestContext testContext;

	public UpdateBookingDetailsStepDefinition(TestContext testContext) {
		this.testContext = testContext;
	}

	@When("user makes a request with valid username and password")
	public void user_makes_a_request_with_valid_username_and_password() {
		String usernameString = PropertiesFile.getProperty("username");
		String password = PropertiesFile.getProperty("password");
		AuthDTO authDTO = new AuthDTO();

		authDTO.setUsername(usernameString);
		authDTO.setPassword(password);

		testContext.response = testContext.req.body(authDTO).post(testContext.session.get("endpoint").toString());
	}

	@Then("token should present in response")
	public void token_should_present_in_response() {

		String responseString = testContext.response.asString();

		JsonPath jsonPath = new JsonPath(responseString);

		System.out.println(responseString);

		testContext.session.put("token", "token=" + jsonPath.get("token"));

	}

	ViewBookingDetailsStepDefinition viewstep;
	ViewBookingDetailsDTO oldBody;

	@When("user makes a request with complete payload")
	public void user_makes_a_request_with_complete_payload() {

		viewstep = new ViewBookingDetailsStepDefinition(testContext);
		Hooks hk = new Hooks(testContext);
		hk.createBooking();
		viewstep.user_makes_a_request_for_the_created_booking_id();
		oldBody = (ViewBookingDetailsDTO) testContext.session.get("Actual Response");
		testContext.session.put("oldBody", oldBody);
		int days = 3;
		updatedbody(days);
		oldBody.getBookingdates().setCheckout(formattedDate);
		oldBody.setTotalprice(price);

		String token = testContext.session.get("token").toString();
		System.out.println(token);
		testContext.response = testContext.req.header("Cookie", token).body(oldBody)
				.put(testContext.session.get("endpoint").toString() + "/"
						+ Integer.parseInt(testContext.session.get("BookingID").toString()));
		System.out.println(testContext.response.asString());
		ViewBookingDetailsDTO responsebody = testContext.response.as(ViewBookingDetailsDTO.class);

		testContext.session.put("UpdatedBody", responsebody);

	}

	@Then("response should contain updated details")
	public void response_should_contain_updated_details() {

		ViewBookingDetailsDTO oldBody = (ViewBookingDetailsDTO) testContext.session.get("oldBody");
		ViewBookingDetailsDTO responsebody = (ViewBookingDetailsDTO) testContext.session.get("UpdatedBody");

		validateResponse(oldBody, responsebody);

	}

	public void validateResponse(ViewBookingDetailsDTO bookingData, ViewBookingDetailsDTO bookingResponse) {
		Assert.assertEquals(bookingResponse.getFirstname(), bookingData.getFirstname());
		Assert.assertEquals(bookingResponse.getLastname(), bookingData.getLastname());
		Assert.assertEquals(bookingResponse.getTotalprice(),
				Integer.parseInt(testContext.session.get("UpdatedPrice").toString()));
		Assert.assertEquals(bookingResponse.isDepositpaid(), bookingData.isDepositpaid());
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckin(), bookingData.getBookingdates().getCheckin());
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckout(),
				testContext.session.get("UpdatedDate").toString());
		Assert.assertEquals(bookingResponse.getAdditionalneeds(), bookingData.getAdditionalneeds());

		LOG.info("Successfully validated the response body");
	}

	@When("user makes a request with partial payload")
	public void user_makes_a_request_with_partial_payload() {
	    // Step 1: Prepare the old body and update it
	    viewstep = new ViewBookingDetailsStepDefinition(testContext);
	  // Ensure booking is created before proceeding
	    viewstep.user_makes_a_request_for_the_created_booking_id();  // Fetch the created booking details
	    oldBody = (ViewBookingDetailsDTO) testContext.session.get("Actual Response");  // Get the old body
	    testContext.session.put("oldBody", oldBody);

	    // Step 2: Update the required fields (totalprice and checkout date)
	    int days = 4; // Example: Update checkout date by 4 days
	    updatedbody(days);  // Updates the checkout date and price


	    // Step 3: Prepare the request body with the updated fields
	    JSONObject reqbody = new JSONObject();
	    reqbody.put("totalprice", price);  // Updated total price

	    JSONObject bookingDates = new JSONObject();
	    bookingDates.put("checkin", oldBody.getBookingdates().getCheckin());  // Retaining the original check-in date
	    bookingDates.put("checkout", formattedDate);  // Updated checkout date
	    reqbody.put("bookingdates", bookingDates);

	    // Step 4: Send the PATCH request with the updated request body
	    String token = testContext.session.get("token").toString();  // Retrieve the token from session
	    int bookingId = Integer.parseInt(testContext.session.get("BookingID").toString());  // Get the booking ID
	    testContext.response = testContext.req
	            .header("Cookie", token)  // Pass token as a cookie
	            .body(reqbody)  // Send the updated body
	            .pathParam("bookingID", bookingId)  // Pass the booking ID in the path
	            .patch(testContext.session.get("endpoint").toString() + "/{bookingID}");  // Send the PATCH request

	    // Step 5: Extract the response body
	    ViewBookingDetailsDTO responseBody = testContext.response.as(ViewBookingDetailsDTO.class);
	    testContext.session.put("UpdatedBody", responseBody);  // Store the updated response body in the session

	    // Log the updated body for reference
	    System.out.println("Updated Body after PATCH request: " + testContext.response.asString());
	}


	String formattedDate;
	int price = 8888;

	public void updatedbody(int days) {

		LocalDate today = LocalDate.now();

		// Add 3 days to today's date
		LocalDate futureDate = today.plusDays(days);

		// Format the future date in yyyy-MM-dd format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formattedDate = futureDate.format(formatter);
		testContext.session.put("UpdatedDate", formattedDate);
		testContext.session.put("UpdatedPrice", price);

	}

}
