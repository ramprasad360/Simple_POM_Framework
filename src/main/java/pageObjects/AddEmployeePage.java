package pageObjects;
import java.time.Duration;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LoggerUtil;

public class AddEmployeePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private Logger log = LoggerUtil.getLogger(this.getClass());

	public AddEmployeePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	private WebElement waitForVisibility(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Page Elements

	@FindBy(xpath = "//div[2]/div/div[2]/div[1]/button")
	private WebElement addBtn;

	@FindBy(name = "firstName")
	private WebElement firstName;

	@FindBy(name = "middleName")
	private WebElement middleName;

	@FindBy(name = "lastName")
	private WebElement lastName;

	@FindBy(xpath = "//label[contains(text(),'Employee Id')]/following::input[1]")
	private WebElement employeeID;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveBtn;

	@FindBy(xpath="//*[@id='oxd-toaster_1']/div/div[1]/div[2]/p[1]")
	private WebElement successMessage;

	//
	//	@FindBy(xpath="//*[@class = 'oxd-topbar-body-nav-tab --visited']/a")
	//	private WebElement highightedSubTab;

	@FindBy(xpath="//*[contains(text(),'Add Employee')]")
	private WebElement addEmployeeSection;

	@FindBy(xpath="//h6[text()='Add Employee']")
	private WebElement addEmployeeHeader;


	@FindBy(xpath="//*[text()='Employee Id already exists']")
	private WebElement employeeExistsMsg;

	// Page Actions

	public void clickOnAddBtn() {
		try {
			log.info("Clicking on Add button.");
			waitForVisibility(addBtn).click();
			waitForVisibility(firstName);
		} catch (TimeoutException e) {
			log.error("Add button is not visible within the timeout.");
		} catch (Exception e) {
			log.error("Error while clicking Add button: " + e.getMessage());
		}
	}

	public void enterEmployeeFirstName(String fName) {
		try {
			log.info("Entering first name: " + fName);
			WebElement element = waitForVisibility(firstName);
			element.click();
			element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			element.sendKeys(Keys.CLEAR);
			waitForVisibility(firstName).sendKeys(fName);
		} catch (Exception e) {
			log.error("Error entering first name: " + e.getMessage());
		}
	}

	public void enterEmployeeMiddleName(String mName) {
		try {
			log.info("Entering middle name: " + mName);
			WebElement element = waitForVisibility(middleName);
			element.click();
			element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			element.sendKeys(Keys.CLEAR);
			waitForVisibility(middleName).sendKeys(mName);
		} catch (Exception e) {
			log.error("Error entering middle name: " + e.getMessage());
		}
	}

	public void enterEmployeeLastName(String lName) {
		try {
			log.info("Entering last name: " + lName);
			WebElement element = waitForVisibility(lastName);
			element.click();
			element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			element.sendKeys(Keys.CLEAR);
			waitForVisibility(lastName).sendKeys(lName);

		} catch (Exception e) {
			log.error("Error entering last name: " + e.getMessage());
		}
	}


	public void enterEmployeeID(String id) {
		try {
			log.info("Entering employee ID: " + id);
			WebElement element = waitForVisibility(employeeID);
			element.click();
			element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			element.sendKeys(Keys.CLEAR);
			element.sendKeys(id);

		} catch (Exception e) {
			log.error("Error entering employee ID: " + e.getMessage());
		}
	}

	public void clickOnSaveBtn() {
		try {
			log.info("Clicking on Save button.");
			waitForVisibility(saveBtn).click();
		} catch (Exception e) {
			log.error("Error while clicking Save button: " + e.getMessage());
		}
	}

	public boolean isSaveButtonDisplayed() {
		try {
			return waitForVisibility(saveBtn).isDisplayed();
		} catch (Exception e) {
			log.warn("Save button is not displayed: " + e.getMessage());
			return false;
		}
	}

	public boolean verifySuccessMessage()
	{
		try 
		{
			return waitForVisibility(successMessage).isDisplayed();
		} catch(Exception e)
		{
			log.warn("Success message is not displayed", e.getMessage());
			return false;
		}
	}

	//	public String getHighlightedSubTabName()
	//	{
	//		return highightedSubTab.getText();
	//
	//	}


	public boolean addEmployee(Map<String, String> employeeData) {
		try {
			String getCurrentURL = driver.getCurrentUrl();
			if(!getCurrentURL.contains("addEmployee"))
			{
				clickOnAddBtn();
			}
			enterEmployeeFirstName(employeeData.get("firstName"));
			enterEmployeeMiddleName(employeeData.get("middleName"));
			enterEmployeeLastName(employeeData.get("lastName"));
			enterEmployeeID(employeeData.get("employeeID"));
			clickOnSaveBtn();


			// Check if employee already exists
			try {
				if (wait.until(ExpectedConditions.visibilityOf(employeeExistsMsg)).isDisplayed()) {
					log.warn("Employee with ID " + employeeData.get("employeeID") + " already exists. Skipping...");
					driver.navigate().refresh();
					return false; // proceed to next employee
				}
			} catch (TimeoutException te) {
				log.info("Employee details saved succesfully!");
				addEmployeeSection.click();
				waitForVisibility(addEmployeeHeader);

				boolean isSuccess = verifySuccessMessage();
				log.info("Verify success msg"+isSuccess);


				if (isSuccess) 
				{
					log.info("Click on Add Employee section");
					addEmployeeSection.click();
					waitForVisibility(addEmployeeHeader);
				}

				return isSuccess;	
			}
			return false;

		} catch (Exception e) {
			log.error("Error adding employee: " + e.getMessage());
			return false;
		}
	}

}