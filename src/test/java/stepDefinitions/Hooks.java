package stepDefinitions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import Utils.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

public class Hooks {
	private static final Logger LOG = LogManager.getLogger(Hooks.class);

	
	CreateBookingStepDefinition createstep;
	TestContext testContext;
	
	
	public Hooks(TestContext testContext)
	{
		this.testContext=testContext;
	}
	@Before("@ViewBookingDetailsByID")
	public void createBooking()
	{
		
		createstep=new CreateBookingStepDefinition(testContext);
		ViewBookingDetailsStepDefinition viewstep=new ViewBookingDetailsStepDefinition(testContext);
		if(testContext.session.get("BookingID")==null)
		{
		createstep.user_has_access_to_endpoint("/booking");
		createstep.user_creates_a_booking_from_json_file_and_the_key("createBookingbody.json", "createBooking1");
		LOG.info("Created BookingID-From Before Hook Method");
		}
	}
	@Before("@updateDetailsByPUT")
	public void createBookingid()
	{
		
		createstep=new CreateBookingStepDefinition(testContext);
		ViewBookingDetailsStepDefinition viewstep=new ViewBookingDetailsStepDefinition(testContext);
		
		createstep.user_has_access_to_endpoint("/booking");
		viewstep.user_makes_a_request_to_view_all_i_ds();
		LOG.info("Created BookingID-From Before Hook Method");
		
	}
	@Before("@DeleteBookingDetails")
	public void createBookingidfordelete()
	{
		
		createBookingid();
		
	}
	
	
	
	

  
}
