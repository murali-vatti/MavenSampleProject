package com.murali.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.murali.base.TestBase;

public class TestUtil extends TestBase{
	
	//public static String screenshotPath;
	public static String screenshotName;

	public  static void captureScreenshot() {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
		Date d = new Date();
		String strDate = sdf.format(d);		
		
		screenshotName =  strDate.toString().replace(":", "_").replace(" ", "_").replace("-", "_").replace(".", "_") + ".jpg";
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\test-output\\html\\" + screenshotName));
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\test-output\\" + screenshotName));
			
			
			//FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
			//FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\test-output\\" + screenshotName));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isTestRunnable(String testCaseName) {


		String sheetName = "RunMode";
		int rows = excel.getRowCount(sheetName);
		
		for (int rNum=2;rNum<=rows;rNum++) {
			
			String testcase = excel.getCellData(sheetName, "TCID", rNum);
			
			if(testcase.equalsIgnoreCase(testCaseName)) {			
				
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else return false;						
			}
			
		}
		
		
		
		return false;
	}
	

	@DataProvider(name="dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}	
	
}
