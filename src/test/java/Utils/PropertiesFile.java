package Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertiesFile {
	
	private static final Logger LOG = LogManager.getLogger(PropertiesFile.class);
	private static FileInputStream fis;
	private static Properties prop = null;

	public static String getProperty(String property) {		
	    try {
	        fis = new FileInputStream(new File(System.getProperty("user.dir")+"//src//test//resources//global.properties"));
	        prop = new Properties();
	        prop.load(fis); // load properties before closing the stream
	    } catch(FileNotFoundException fnfe) {
	        LOG.error("Properties File Not Found", fnfe);
	    } catch(IOException ioe) {
	        LOG.error("IO Exception while loading Properties File", ioe);
	    } 
	    
	    return prop.getProperty(property); // return the property value after loading the properties
	}

}