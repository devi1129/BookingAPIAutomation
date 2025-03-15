package stepDefinitions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import Utils.TestContext;
import io.cucumber.java.en.When;

public class DeleteBookingStepDefinition {

	TestContext testContext;
	private static final Logger LOG = LogManager.getLogger(DeleteBookingStepDefinition.class);

	public DeleteBookingStepDefinition(TestContext context) {
		this.testContext = context;
	}

	@When("user makes a request to delete")
	public void user_makes_a_request_to_delete() {
		String token = testContext.session.get("token").toString(); // Retrieve the token from session
		int bookingId = Integer.parseInt(testContext.session.get("BookingID").toString()); // Get the booking ID
		testContext.response = testContext.req.header("Cookie", token) // Pass token as a cookie
				// Send the updated body
				.pathParam("bookingID", bookingId) // Pass the booking ID in the path
				.delete(testContext.session.get("endpoint").toString() + "/{bookingID}");

	}

}
