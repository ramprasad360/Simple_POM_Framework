package pageObjects;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import utils.*;
import org.apache.logging.log4j.Logger;

public class LoginPage 
{
	WebDriver driver;
	WebDriverWait wait;
	private Logger log = LoggerUtil.getLogger(this.getClass());
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
		wait.until(ExpectedConditions.visibilityOf(userName));
		userName.clear();
		userName.sendKeys(user);
	}

	public void enterPassword(String psw)
	{
		log.info("Entering password: " + psw);
		wait.until(ExpectedConditions.visibilityOf(password));
		password.clear();
		password.sendKeys(psw);
	}
	
	public void clickLogin()
	{
		log.info("Clicking on login button");
		wait.until(ExpectedConditions.visibilityOf(loginBtn));
		loginBtn.click();
	}	
}
