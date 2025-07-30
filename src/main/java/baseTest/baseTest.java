package baseTest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.*;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.*;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtil;

public class baseTest
{
	protected WebDriver driver;
	protected Properties prop;
	protected static ExtentReports reports;
	protected ExtentTest test; 
    protected Logger log = LoggerUtil.getLogger(this.getClass());

	public String takeScreenshot(String testName) {
	    String dateName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    String screenshotFolder = "test-output/screenshots/";
	    String screenshotName = testName + "_" + dateName + ".png";
	    String fullPath = screenshotFolder + screenshotName;

        File folder = new File(screenshotFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
	    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    try {
	        FileHandler.copy(srcFile, new File(fullPath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "../screenshots/" + screenshotName; 
	}


	@BeforeSuite
	public void initReport()
	{
		log.info("Initializing ExtentReport...");
		reports = ExtendReportManager.createInstance("RegressionReport");
	}

	@BeforeMethod
	public void setUp(Method method)
	{
		prop = ConfigReader.initProperties();
		String url = prop.getProperty("baseUrl");
		driver = DriverFactory.initDriver(url);
		test = reports.createTest(method.getName());
		ExtendReportManager.setTest(test);
	}

	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			utils.ExtendReportManager.getTest().log(Status.FAIL, "Test Failed "+result.getThrowable());
			String screenshotPath = takeScreenshot(result.getName());
			try
			{
				utils.ExtendReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.skip("Test Skipped");
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			utils.ExtendReportManager.getTest().log(Status.PASS, "Test Passed");
		}

		DriverFactory.quitDriver();
	}

	@AfterSuite
	public void flushReport()
	{
		log.info("Flushing ExtentReport...");
		reports.flush();
	}
}