package com.murali.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.murali.base.TestBase;
import com.murali.utilities.TestUtil;
import com.relevantcodes.extentreports.*;


public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alertText,String runmode)
			throws InterruptedException {

		if (!TestUtil.isTestRunnable("AddCustomerTest")) {

			throw new SkipException("Skipping the test AddCustomerTest as the Runmode is NO");
		}

		

		if(!runmode.equals("Y")) {
			
			throw new SkipException("Skipping the test case as the Runmode is NO");
		}
		
		test.log(LogStatus.INFO, "Adding customer");

		click("addCustBtn_CSS");
		type("firstname_CSS", firstName);
		type("lastname_XPATH", lastName);
		type("postcode_CSS", postCode);
		click("addbtn_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		// Assert.assertTrue(alert.getText().contains(alertText));
		verifyContains(alert.getText(), alertText);

		alert.accept();
		log.debug("Customer added Successfully....");

	}

}
