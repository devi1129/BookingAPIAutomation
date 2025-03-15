package Utils;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
	
static	String jsonfilepathString=new File(PropertiesFile.getProperty("test.jsonfile.path")).getAbsolutePath()+File.separator;

	private static JSONParser parser = new JSONParser();
	private static Object body;
	
	public static String getrequestBody(String filename,String key)
	{
		try {
			body = ((JSONObject)parser.parse(new FileReader(jsonfilepathString+filename))).get(key);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("JSON file not found at path: " + jsonfilepathString+filename);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading file: " +filename);
		} catch (ParseException e) {
			throw new RuntimeException("Parse Exception occured while Parsing: " + filename);
		}
		return body.toString();
		
		
		
		
	

		
	}
}
