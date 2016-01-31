package com.technosoft.auto.framework.chunks;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public abstract class AppiumDriverActivities {

	@SuppressWarnings("rawtypes")
	public static IOSDriver driver = null;
	public static ExtentTest extentTest = null;
	private static String workingDirectory = System.getProperty("user.dir");
	private static String screenShotPath = workingDirectory + "/screenshot/";
	private static String sanityTest = "Sanity Test";
	//private static final boolean rfrRun = false;

	public static String getSanityTest() {
		return sanityTest;
	}
	
	/*
	 * Methods executes Before test suite starts
	 */
	@BeforeSuite(enabled = Settings.mobileRun)
	public void beforeSuite() {
		MobileReports.getInstance();
	}

	/*
	 * Methods executes After all test activities are done
	 */
	@AfterSuite(enabled = Settings.mobileRun)
	public void afterSuite() {
		MobileReports.flush();
		MobileReports.testSuiteCleanUp();
	}

	/*
	 * Takes Screenshot when test fails and attaches to the Extent Test Reports
	 * and this test case only gets activated only if test fails
	 */

	@AfterMethod(enabled = Settings.mobileRun)
	public void handleScreenShot(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path = captureScreenShot(driver, result.getName());
			String image = extentTest.addScreenCapture(screenshot_path);
			extentTest.log(LogStatus.FAIL,
					"Failed Method Name: " + result.getName(), image);
			MobileReports.endTheTest(extentTest);
		}

	}


	
	@BeforeClass(enabled = Settings.mobileRun)
	public static void getDriver()
			throws MalformedURLException {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
					"Appium");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
					"iOS");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
					"9.2");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
					"MohammadiPad");
			capabilities.setCapability("udid",
					"03d67698293cf3dfad92f7799ea95cc8cc834eaa");
			capabilities.setCapability("bundleId", "com.reserve.rfr.sandbox");
			driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"),
					capabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} 
	

	// Closes all browser opened by the driver
	@AfterClass(enabled = Settings.mobileRun)
	public static void tearDownIOSDriver() {
		driver.quit();
	
	}

	public static String captureScreenShot(WebDriver driver,
			String screenShotName) {

		try {
			TakesScreenshot screenShot = (TakesScreenshot) driver;
			File source = screenShot.getScreenshotAs(OutputType.FILE);
			String dest = screenShotPath + screenShotName + ".png";
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Taken");
			return dest;
		} catch (Exception e) {
			System.out.println("Exception while taking Screenshot "
					+ e.getMessage());
			return e.getMessage();
		}

	}



	/*
	 * Specifies the test name and test group before test
	 */
	public static ExtentTest startTest(String enterTestName,
			String enterTestGroup) {
		extentTest = MobileReports.startTest(enterTestName)
				.assignCategory(enterTestGroup);
		return extentTest;

	}

	/*
	 * Ends the test with pass status
	 */
	public static void endTestWithPassStatus(String enterMessage) {
		extentTest.log(LogStatus.PASS, enterMessage);
		MobileReports.endTheTest(extentTest);
	}

	/*
	 * Report displays warning where it applies
	 */
	public static void getWarning(String enterMessage) {
		extentTest.log(LogStatus.WARNING, enterMessage);
	}

	/*
	 * Specifies test step name
	 */
	public static void getTestStepName(String enterTestStep) {
		extentTest.log(LogStatus.PASS, enterTestStep);
	}

	/*
	 * Specifies test info
	 */

	public static void getTestInfo(String enterTestInfo) {
		extentTest.log(LogStatus.INFO, enterTestInfo);
	}
	

}
