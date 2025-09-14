package pageObjects;
import java.beans.Visibility;
import java.time.Duration;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.LoggerUtil;

public class NavigationLinks 
{
	WebDriver driver;
	WebDriverWait wait;
	private Logger log = LoggerUtil.getLogger(this.getClass());

	public NavigationLinks(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	// page elements
	@FindBy(xpath = "//span[text()='PIM']")
	private WebElement menu_PIM;

	@FindBy(xpath="//h6[text()='PIM']")
	private WebElement header_PIM;

	
	//page methods	
	public void clickOnMenuPIM() {
	    try {
	        // Wait for the PIM link to be clickable
	        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(menu_PIM));
	        element.click();
	        log.info("Clicked on the PIM menu.");
	    } catch (TimeoutException e) {
	        log.error("PIM menu was not clickable within the timeout period.");
	    } catch (Exception e) {
	        log.error("An error occurred while clicking on the PIM menu: " + e.getMessage());
	    }
	}
}
