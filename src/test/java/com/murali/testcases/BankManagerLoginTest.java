package com.murali.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.murali.base.TestBase;
import com.murali.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;


public class BankManagerLoginTest extends TestBase {	

	@Test
	public void bankManagerLoginTest() throws IOException  {	
		
		
		if(!TestUtil.isTestRunnable("BankManagerLoginTest")) {
			
			throw new SkipException("Skipping the test BankManagerLoginTest as the Runmode is NO");
		}
		
		
		log.debug("Inside Login Test....");
		test.log(LogStatus.INFO, "Login bank manager");
		click("btnManager_CSS");
		//TestUtil.captureScreenshot();		
	
		//Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful...");
		verifyEquals("Add Customer", driver.findElement(By.cssSelector(OR.getProperty("addCustBtn_CSS"))).getText() );
		
		
		log.debug("Login Successfully executed ....");	
		//Assert.fail("Bank Manager Login Fail");
		 
	
		
	}
}
