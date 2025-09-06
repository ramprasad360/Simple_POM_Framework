package baseTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import pageObjects.LoginPage;
import utils.*;

import org.apache.logging.log4j.Logger;

public class baseTest {

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
    public void initReportAndConfig() {
        log.info("Initializing ExtentReport and Config...");
        reports = ExtendReportManager.createInstance("RegressionReport");
        prop = ConfigReader.initProperties();
    }

    @BeforeClass
    public void loginOncePerClass() throws IOException {
        log.info("Launching browser and performing login...");
        String url = prop.getProperty("baseUrl");
        driver = DriverFactory.initDriver(url);

        String user = prop.getProperty("username");
        String password = prop.getProperty("password");
        String expectedTitle = "OrangeHRM";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName(user);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Login failed - Title mismatch.");
    }

    @BeforeMethod
    public void createTestLog(Method method) {
        test = reports.createTest(method.getName());
        ExtendReportManager.setTest(test);
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtendReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
            String screenshotPath = takeScreenshot(result.getName());
            try {
                ExtendReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtendReportManager.getTest().log(Status.PASS, "Test Passed");
        }
    }

    @AfterClass
    public void closeDriverAfterClass() {
        log.info("Closing browser...");
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void flushReport() {
        log.info("Flushing ExtentReport...");
        reports.flush();
    }
}
