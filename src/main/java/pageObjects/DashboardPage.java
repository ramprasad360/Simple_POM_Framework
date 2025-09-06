package pageObjects;

import java.time.Duration;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import utils.LoggerUtil;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;
    private Logger log = LoggerUtil.getLogger(this.getClass());

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Page elements
    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashboardHeader;

    // Page methods
    public boolean isDashboardHeaderDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dashboardHeader));
            log.info("User is on Dashboard page");
            return true;
        } catch (TimeoutException e) {
            log.error("Dashboard header not visible, user might not be on Dashboard page");
            return false;
        }
    }
}
