package cucumberOptions;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features/", glue = "stepDefinitions", monochrome = true, plugin = {
		"html:target/cucumber.html", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-report.json", "rerun:target/failed_scenarios.txt"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

	// scenarios class exists in Abstracttestngcucumberclass
	// we are overriding that method from parent class
	// to execute tests parallely

//	@Override
//	@DataProvider(parallel = true)
//	public Object[][] scenarios() {
//		return super.scenarios();
//	}

}

/*
 * for extent reports add avent stack extent reports and grasshopper
 * dependencies for dependency injection for test context set up add pico
 * container dependency to run testng add cucumber-testng dependency
 * 
 */
