package Utils;
import java.lang.classfile.AnnotationValue.OfAnnotation;
import java.util.HashMap;
import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
	
	public RequestSpecification req;
	public Response response;
	public Map<String, Object> session = new HashMap<String, Object>();

	public RequestSpecification getrequestspec()
	{	
	    if(req==null) 
	    {
		req = new RequestSpecBuilder().setBaseUri(PropertiesFile.getProperty("baseUrl")).setContentType(ContentType.JSON).
				build();	
		
		
	    }
		return req;
	   
	}
	
	
	
	

}
