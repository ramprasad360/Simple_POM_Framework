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
	private LoginPage loginPage ;

    @BeforeMethod
    public void setupTest() {
        loginPage = new LoginPage(driver);
    }
    
	@Test(priority=1,enabled=true)
	public void test_01() 
	{
		String user = "Admin";
		String password = "Admin123";
		String expectedTitle = "OrangeHRM";
		
		loginPage.enterUserName(user);
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		
		
	}
}
