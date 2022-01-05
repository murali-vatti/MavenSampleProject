package com.murali.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.murali.utilities.ExcelReader;
import com.murali.utilities.ExtentManager;
import com.murali.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase extends ExtentManager {

	public static WebDriver driver = null;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test=null;

	public static String filename = "";
	static Date d = new Date();
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	@BeforeSuite
	public void setUp() {

		// Initialising config properties file
		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded....");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Initialising Object repository properties file
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("Object repository file loaded....");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Setting-up the browser driver

		if (config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("Chrome launched....");
		} else if (config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			log.debug("Firefox launched....");
		} else if (config.getProperty("browser").equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			log.debug("Edge launched....");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to :" + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;
		}

	}

	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}

		test.log(LogStatus.INFO, "Clicking on : " + locator);

	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		 
		test.log(LogStatus.INFO, "Typing in :" + locator + " entered values as  " + value);
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown=driver.findElement(By.id(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		}
		 
		Select select = new Select(dropdown);		
		select.selectByVisibleText(value);		
		
		
		
		//select.selectByIndex(1);
		
		
		test.log(LogStatus.INFO, "Selecting from dropwon in :" + locator + " selected values as  " + value);	
		
	}
	
	public static void verifyEquals(String expected, String actual) {
		
				
		try{
			Assert.assertEquals(expected, actual);
			test.log(LogStatus.INFO,"Verification pass");
			log.debug("Verification successful" + "Expected :" + expected + "Actual :" + actual);	
		}catch (Throwable t) {
			
			TestUtil.captureScreenshot();
			Reporter.log("<br>" + "Verification failure :" + t.getMessage() + "<br>");
			Reporter.log("<a target = \"_blank\" href=" + TestUtil.screenshotName +"> <img src="+TestUtil.screenshotName+" width=150 height=150></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			test.log(LogStatus.FAIL, " Verification failed with exception  :" + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));			
			
		}		
	}
	public static void verifyContains( String actual,String expected) {
		
		
		try{
			Assert.assertTrue(actual.contains(expected));
			test.log(LogStatus.INFO,"Verification pass");
			log.debug("Verification successful" + "Expected :" + expected + "Actual :" + actual);	
		}catch (Throwable t) {
			
			TestUtil.captureScreenshot();
			Reporter.log("<br>" + "Verification failure :" + t.getMessage() + "<br>");
			Reporter.log("<a target = \"_blank\" href=" + TestUtil.screenshotName +"> <img src="+TestUtil.screenshotName+" width=150 height=150></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			test.log(LogStatus.FAIL, " Verification failed with exception  :" + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));			
			
		}		
	}
	
	

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

		log.debug("Test execution completed .....");
	}

}
