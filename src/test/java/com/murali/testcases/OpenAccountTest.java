package com.murali.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.murali.base.TestBase;
import com.murali.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(String customer,String currency,String alertText, String runmode) throws InterruptedException {
		
		
	if(!TestUtil.isTestRunnable("OpenAccountTest")) {
			
			throw new SkipException("Skipping the test OpenAccountTest as the Runmode is NO");
		}
		
	
	if(!runmode.equals("Y")) {
		
		throw new SkipException("Skipping the test case as the Runmode is NO");
	}
		
	 
		
		test.log(LogStatus.INFO, "Opening Account");			
		click("openAccountBtn_CSS");		
		select("customer_CSS",customer);	
		select("currency_CSS",currency);
		
		TestUtil.captureScreenshot();
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		
		click("processBtn_XPATH");	 
		 		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		verifyContains(alert.getText(), alertText);		
		alert.accept();		
		 	
		log.debug("Account opened Successfully....");		
		
	}

}
