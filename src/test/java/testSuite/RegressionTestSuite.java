package testSuite;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import baseTest.*;
import pageObjects.*;
import utils.DriverFactory;

public class RegressionTestSuite extends baseTest
{
	@Test(description= "to validate the login functionality",priority=1,enabled=true)
	public void test_01() 
	{
		//step 1
		System.out.println("Succesfully into the dashboard page");
	}
}