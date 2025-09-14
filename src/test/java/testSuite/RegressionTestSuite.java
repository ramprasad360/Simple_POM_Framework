package testSuite;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import baseTest.baseTest;
import pageObjects.*;
import utils.ExcelUtil;
import utils.ConfigReader;
import java.util.List;
import java.util.Map;

public class RegressionTestSuite extends baseTest {

	@DataProvider(name = "EmployeeData")
	public Object[][] getEmployeeData() {
		String filePath = ConfigReader.getProperty("excelPath");
		ExcelUtil excel = new ExcelUtil(filePath);
		List<Map<String, String>> data = excel.getData("Sheet1");

		Object[][] testData = new Object[data.size()][1];
		for (int i = 0; i < data.size(); i++) {
			testData[i][0] = data.get(i);
		}
		return testData;
	}

	@Test(priority=1,enabled=true,dataProvider = "EmployeeData", description = "Verify Add Employee functionality")
	public void verify_add_employee_functionality(Map<String, String> employeeData) {

		DashboardPage dashboardPage = new DashboardPage(driver);
		if(dashboardPage.isDashboardHeaderDisplayed()==true)
		{
			log.info("User is on Dashboard page");
			NavigationLinks navigationLinks = new NavigationLinks(driver);
			log.info("User navigating to PIM page");
			navigationLinks.clickOnMenuPIM();
			AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
			boolean result = addEmployeePage.addEmployee(employeeData);
			log.info(result);
		}

		else
		{
			AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
			boolean result = addEmployeePage.addEmployee(employeeData);
			log.info(result);
		}
	}
}