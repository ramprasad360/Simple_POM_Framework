package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.*;

public class DriverFactory 
{
	public static WebDriver driver;

	public static WebDriver initDriver(String url) throws IOException
	{
		if(driver==null)
		{
			Properties prop = ConfigReader.initProperties();
			String browser = prop.getProperty("browser").toLowerCase();

			switch(browser) {

			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--remote-allow-origins=*",
						"--disable-infobars",
						"--disable-notifications",
						"--disable-extensions",
						"--ignore-certificate-errors");
				driver = new ChromeDriver(chromeOptions);
				break;


			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.addPreference("dom.webnotifications.enabled", false);
				driver = new FirefoxDriver(firefoxOptions);
				break;

			default:
				throw new RuntimeException("Unsupported browser: " + browser);

			}
			driver.manage().window().maximize();
			driver.get(url);
		}
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