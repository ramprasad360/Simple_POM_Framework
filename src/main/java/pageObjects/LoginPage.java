package pageObjects;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.testng.annotations.*;
import utils.*;
import org.apache.logging.log4j.Logger;

public class LoginPage 
{
	WebDriver driver;
	private Logger log = LoggerUtil.getLogger(this.getClass());
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//page elements
	
	@FindBy(name = "username")
	private WebElement userName;
	
	@FindBy(name="password")
	private WebElement password;
	
	@FindBy(xpath = "//*[@type='submit']")
	private WebElement loginBtn;
	
	
//page methods
	
	public void enterUserName(String user)
	{
		log.info("Entering username: " + user);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		userName.clear();
		userName.sendKeys(user);
	}

	public void enterPassword(String psw)
	{
		log.info("Entering password: " + psw);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		password.clear();
		password.sendKeys(psw);
	}
	
	public void clickLogin()
	{
		log.info("Clicking on login button");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		loginBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}	
}
