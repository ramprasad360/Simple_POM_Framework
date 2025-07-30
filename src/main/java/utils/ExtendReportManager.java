package utils;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.*;
import io.opentelemetry.sdk.trace.internal.*;

public class ExtendReportManager {

	private static ExtentReports reports;
    private static ExtentTest test;


    public static ExtentReports createInstance(String reportName)
    {
    	ExtentSparkReporter spark = new ExtentSparkReporter("./test-output"+reportName+".html");
    	spark.config().setReportName("Regression Test Report");
    	spark.config().setDocumentTitle("Test Execution Report");
    	spark.config().setTheme(Theme.DARK);
    	
    	reports = new ExtentReports();
    	reports.attachReporter(spark);
    	reports.setSystemInfo("OS Name", System.getProperty("os.name"));
    	reports.setSystemInfo("OS Version", System.getProperty("os.version"));
    	reports.setSystemInfo("Java Version", System.getProperty("java.version"));
    	reports.setSystemInfo("User Name", System.getProperty("user.name"));
    	return reports;
    }
    
    public static ExtentTest getTest()
    {
    	return test;
    }
    
    public static void setTest(ExtentTest testRef)
    {
    	test = testRef;
    }
}
