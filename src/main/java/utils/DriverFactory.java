package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.*;

public class DriverFactory 
{
	public static WebDriver driver;
	
	public static WebDriver initDriver(String url)
	{
		if(driver==null)
		{
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*","--disable-infobars","--disable-notifications","--disable-extensions","--ignore-certificate-errors");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		}
			driver.get(url);
		return driver;
	}

	public static void quitDriver()
	{
		if(driver!=null)
		{
			driver.quit();
			driver = null;
		}
	}
}
