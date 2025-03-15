package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import Utils.ExcelUtils;
import Utils.JsonReader;
import Utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import model.CreateBookingDTO;

public class CreateBookingStepDefinition {

	TestContext testContext;
	private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);

	public CreateBookingStepDefinition(TestContext context) {
		this.testContext = context;
	}

	@Given("user has access to endpoint {string}")
	public void user_has_access_to_endpoint(String Endpoint) {
		testContext.session.put("endpoint", Endpoint);

		testContext.req = given().spec(testContext.getrequestspec());

		LOG.info("RequestSpecification initialized with endpoint: " + Endpoint);
		LOG.info("Given step Req object: " + testContext.req);
	}

	@When("user creates a booking")
	public void user_creates_a_booking(DataTable dataTable) {
		Map<String, String> bookingData = dataTable.asMaps().get(0);

		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", bookingData.get("firstname"));
		bookingBody.put("lastname", bookingData.get("lastname"));
		bookingBody.put("totalprice", Integer.parseInt(bookingData.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.parseBoolean(bookingData.get("depositpaid")));

		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", bookingData.get("checkin"));
		bookingDates.put("checkout", bookingData.get("checkout"));
		bookingBody.put("bookingdates", bookingDates);

		bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

		LOG.info("When step Req object: " + testContext.req.toString());
		LOG.info("Request Body: " + bookingBody.toString());

		testContext.response = testContext.req.log().all().body(bookingBody.toString())
				.post(testContext.session.get("endpoint").toString());

		LOG.info("Successfully sent the POST request to create booking.");

		if (testContext.response != null) {
			try {
				CreateBookingDTO responseBody = testContext.response.as(CreateBookingDTO.class);
				
				LOG.info("Successfully extracted response body.");
				validateResponse(new JSONObject(bookingData), responseBody);
			} catch (Exception e) {
				LOG.error("Error converting response to DTO", e);
				Assert.fail("Error converting response to DTO: " + e.getMessage());
			}

		} else {
			LOG.error("Response is null");
			Assert.fail("Response is null");
		}
	}

	@Then("user should get the response code {int}")
	public void user_should_get_the_response_code(int code) {
		testContext.response.then().statusCode(code);
		LOG.info("Successfully validated the response code: " + code);
	}

	@Then("user validates the response with JSON schema {string}")
	public void user_validates_the_response_with_json_schema(String schema) {
		testContext.response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schema));
		LOG.info("Successfully validated the response schema: " + schema);
	}

	public void validateResponse(JSONObject bookingData, CreateBookingDTO bookingResponse) {
		Assert.assertEquals(bookingResponse.getBooking().getFirstname(), bookingData.get("firstname"));
		Assert.assertEquals(bookingResponse.getBooking().getLastname(), bookingData.get("lastname"));
		Assert.assertEquals(bookingResponse.getBooking().getTotalprice(), Integer.parseInt(bookingData.get("totalprice").toString()));
		Assert.assertEquals(bookingResponse.getBooking().getDepositpaid(), Boolean.parseBoolean(bookingData.get("depositpaid").toString()));
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(), bookingData.get("checkin"));
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(), bookingData.get("checkout"));
		Assert.assertEquals(bookingResponse.getBooking().getAdditionalneeds(), bookingData.get("additionalneeds"));

		LOG.info("Successfully validated the response body");
	}

	@When("user creates a booking using {string} from sheet {string}")
	public void user_creates_a_booking_using_from_sheet(String datakey, String sheetname) {
		int columnindex=ExcelUtils.getCellIndexByHeader(sheetname, "dataKey");
		int rownum=ExcelUtils.getRownum(sheetname, columnindex, datakey);
		testContext.session.put("datakeyrownum", rownum);
	    int requestbodycolumn=ExcelUtils.getCellIndexByHeader(sheetname, "requestBody");
	    testContext.session.put("sheetname", sheetname);
	    
	    int excelresponsecolumn=ExcelUtils.getCellIndexByHeader(sheetname, "responseBody");
	    
	    
	    String requestbody=ExcelUtils.getCellData(sheetname, rownum, requestbodycolumn);
	    String excelresponseString=ExcelUtils.getCellData(sheetname, rownum, excelresponsecolumn);
	    
		testContext.response = testContext.req.log().all().body(requestbody)
				.post(testContext.session.get("endpoint").toString());

		LOG.info("Successfully sent the POST request to create booking.");

		if (testContext.response != null) {
			try {
				CreateBookingDTO dtoresponse = testContext.response.as(CreateBookingDTO.class);
				System.out.println(dtoresponse.toString());
				LOG.info("Successfully extracted response body.");
				validateResponse(new JSONObject(excelresponseString.toString()), dtoresponse);
			} catch (Exception e) {
				LOG.error("Error converting response to DTO", e);
				Assert.fail("Error converting response to DTO: " + e.getMessage());
			}

		} else {
			LOG.error("Response is null");
			Assert.fail("Response is null");
		}
		

	}

	@Then("user validates the response schema from excelcolumn {string}")
	public void user_validates_the_response_schema_from_excelcolumn(String columnName) {

		
		int columnindex=ExcelUtils.getCellIndexByHeader(testContext.session.get("sheetname").toString(), columnName);
		
		int rownum=Integer.parseInt(testContext.session.get("datakeyrownum").toString());
		
		String responsechema=ExcelUtils.getCellData(testContext.session.get("sheetname").toString(),rownum,columnindex);
		testContext.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(responsechema));
		LOG.info("Successfully Validated schema from Excel");

	}
	
	@When("user creates a Booking from json file {string} and the key {string}")
	public void user_creates_a_booking_from_json_file_and_the_key(String filename, String key) {
		
		String requestbody=JsonReader.getrequestBody(filename, key);
		
		
		testContext.response = testContext.req.log().all().body(requestbody)
				.post(testContext.session.get("endpoint").toString());

		LOG.info("Successfully sent the POST request to create booking.");

		if (testContext.response != null) {
			try {
				CreateBookingDTO dtoresponse = testContext.response.as(CreateBookingDTO.class);
				testContext.session.put("Expected Response", (CreateBookingDTO)dtoresponse);
				testContext.session.put("BookingID", dtoresponse.getBookingid());
				System.out.println(dtoresponse.toString());
				LOG.info("Successfully extracted response body.");
				
			} catch (Exception e) {
				LOG.error("Error converting response to DTO", e);
				Assert.fail("Error converting response to DTO: " + e.getMessage());
			}

		} else {
			LOG.error("Response is null");
			Assert.fail("Response is null");
		}
		

	   
	}


}