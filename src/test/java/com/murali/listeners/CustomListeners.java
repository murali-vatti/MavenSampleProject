package com.murali.listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;


import com.murali.base.TestBase;
import com.murali.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;


public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		test = rep.startTest(result.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		TestUtil.captureScreenshot();	
		test.log(LogStatus.PASS, result.getName().toUpperCase() +   "   PASS");
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		Reporter.log("Click to see screenshot");
		
		
		Reporter.log("<a target = \"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target = \"_blank\" href=" + TestUtil.screenshotName +"> <img src="+TestUtil.screenshotName+" width=150 height=150></img></a>");
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		TestUtil.captureScreenshot();
		test.log(LogStatus.FAIL, result.getName().toUpperCase() +   " Failed with exception " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		
		Reporter.log("Test Failure screenshot");
		 
		
		Reporter.log(
				"<a target = \"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log(
				"<a target = \"_blank\" href=" + TestUtil.screenshotName +"> <img src="+TestUtil.screenshotName+" width=150 height=150></img></a>");

		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
		test.log(LogStatus.SKIP, "Skipping the test " + result.getName().toUpperCase() +" as RunMode is NO");
		rep.endTest(test);
		rep.flush();
		
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}
	 

}
