package com.technosoft.auto.framework.chunks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Description: This class resides all webdriver activities which will be used
 * throughout all test classes, description for each method is specfied before
 * all methods
 * 
 * @author mmuntakim
 *
 */

public abstract class WebDriverActivities {

	public static WebDriver driver = null;
	private static DesiredCapabilities caps = null;
	public static ExtentTest extentTest = null;
	private static String workingDirectory = System.getProperty("user.dir");
	private static String screenShotPath = workingDirectory + "/screenshot/";
	private static String chromePath = workingDirectory
			+ "/RemoteDrivers/chromedriver";
	private static String sauceURL = "http://ccraigc:acd67d54-0feb-44ce-9ca8-c10c9594e5fb@ondemand.saucelabs.com:80/wd/hub";
	private static String buildNumber = "Build # 101";
	public static String browserName = null;
	private static String testName = "Sample Regression Test";

	// private static final boolean widgetRun = true;

	/**
	 * Description: Actions inside of this method gets executed before
	 * individual test suite starts
	 * 
	 * @author mmuntakim
	 * @throws Exception 
	 */
	@BeforeSuite(enabled = Settings.webApplicationRun)
	public void beforeSuite() throws Exception {
		Report.getInstance();
	
	}

	/**
	 * Description: Actions inside of this method gets executed after individual
	 * test suite ends
	 * 
	 * @author mmuntakim
	 * @throws Exception 
	 */
	@AfterSuite(enabled = Settings.webApplicationRun)
	public void afterSuite() throws Exception {
		Report.flush();
		Report.testSuiteCleanUp();
	
	}

	/**
	 * Description: Takes screenshot after each method
	 *         if test script fails
	 * @author mohammadmuntakim1 
	 * @param result
	 */

	@AfterMethod(enabled = Settings.webApplicationRun)
	public void handleScreenShot(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path = captureScreenShot(driver, result.getName());
			String image = extentTest.addScreenCapture(screenshot_path);
			extentTest.log(LogStatus.FAIL,
					"Failed Method Name: " + result.getName(), image);
			Report.endTheTest(extentTest);
		}

	}

	/**
	 * Description: Test will execute in specfied browser
	 *         which will be passeed on testNG xml parameter value and it also
	 *         will display the browser details in reports
	 * @author mmuntakim 
	 * @param browser
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	/*
	 * Executes test on browser which is specified in browser parameter
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Parameters("browser")
	@BeforeClass(enabled = Settings.webApplicationRun)
	public static void getDriver(String browser) throws Exception {
		if (browser.equalsIgnoreCase("firefox")) {
			// Runs on Local Firefox
			driver = new FirefoxDriver();
			browserName = browser;
		} else if (browser.equalsIgnoreCase("iPhone_ios")) {
			
			DesiredCapabilities iosCap = new DesiredCapabilities();
			iosCap.setCapability(MobileCapabilityType.AUTOMATION_NAME,
					"Test on Ios Device");
			iosCap.setCapability(MobileCapabilityType.PLATFORM_NAME,
					MobilePlatform.IOS);
			iosCap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
			 iosCap.setCapability(MobileCapabilityType.BROWSER_NAME,
			 "safari");
			iosCap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
			iosCap.setCapability("nativeWebTap", true);
			// Next two line only for real devices
//			iosCap.setCapability("udid",
//					"fcf9e0603512eeb411495a6e6c399219a06268c4");
//			iosCap.setCapability("app", "com.mohammad.safariLuncher");
			driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"),
					iosCap);
			Thread.sleep(5000);
			Set<String> contextHandles = ((AppiumDriver) driver)
					.getContextHandles();
			
			for (String contextHandle : contextHandles) {
				System.out.println("+++++++++"+contextHandle);
				if (contextHandle.contains("WEBVIEW")) {
					((AppiumDriver) driver).context(contextHandle);
					Thread.sleep(10000);
					
				}
				
			}
			browserName = browser;
		} else if (browser.equalsIgnoreCase("android")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", "5.0");
			capabilities.setCapability("deviceName", "f3cf9a24");
			capabilities.setCapability("appPackage", "com.android.chrome");
			capabilities.setCapability("appActivity", "Main");
			driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),
					capabilities);
			browserName = browser;

		} else if (browser.equalsIgnoreCase("chrome")) {
			// Runs on Local Chrome
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_46.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "46.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_45.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "45.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_44.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "44.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_43.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "43.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_42.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "42.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_41.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "41.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_40.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "40.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_39.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "39.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_30.0_Win8.1")) {
			sauceConfigTemplateChrome("Windows 8.1", "30.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_46.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "46.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_45.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "45.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_44.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "44.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_43.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "43.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_42.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "42.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_41.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "41.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_40.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "40.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("Chrome_39.0_Mac")) {
			sauceConfigTemplateChrome("OS X 10.10", "39.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("IE_9.0_Win7")) {
			sauceConfigTemplateIE("Windows 7", "9.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("IE_10.0_Win7")) {
			sauceConfigTemplateIE("Windows 7", "10.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("IE_11.0_Win8.1")) {
			sauceConfigTemplateIE("Windows 8.1", "11.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_20.0_Win7")) {
			sauceConfigTemplateFirefox("Windows 7", "20.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_35.0_Win7")) {
			sauceConfigTemplateFirefox("Windows 7", "35.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_38.0_Win7")) {
			sauceConfigTemplateFirefox("Windows 7", "38.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_39.0_Win7")) {
			sauceConfigTemplateFirefox("Windows 7", "39.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_40.0_Win7")) {
			sauceConfigTemplateFirefox("Windows 7", "40.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_41.0_Win8.1")) {
			sauceConfigTemplateFirefox("Windows 8.1", "41.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_35.0_Mac")) {
			sauceConfigTemplateFirefox("OS X 10.10", "35.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_38.0_Mac")) {
			sauceConfigTemplateFirefox("OS X 10.10", "38.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_39.0_Mac")) {
			sauceConfigTemplateFirefox("OS X 10.10", "39.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_40.0_Mac")) {
			sauceConfigTemplateFirefox("OS X 10.10", "40.0");
			browserName = browser;
		} else if (browser.equalsIgnoreCase("firefox_41.0_Mac")) {
			sauceConfigTemplateFirefox("OS X 10.10", "41.0");
			browserName = browser;

		} else {
			System.out
					.println("=======================Invalid Browser Specified========================");
		}
		getURL();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// driver.manage().window().maximize();

	}

	/**
	 * Description: While performing web testing on mobile browser,
	 *            user must switch context from mobile to webview, this method
	 *            will switch context from mobile to webview
	 * @mmuntakim 
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void switchToWebview() throws InterruptedException {
		Set<String> contextHandles = ((AppiumDriver) driver)
				.getContextHandles();
		for (String contextHandle : contextHandles) {
			if (contextHandle.contains("WEBVIEW")) {
				((AppiumDriver) driver).context(contextHandle);
				// Thread.sleep(5000);
			}
		}
	}

	/**
	 * 
	 * Description: Use this method when setting up Chrome
	 *         browser environment in saucelabs
	 * @author mmuntakim 
	 * @param platform
	 * @param version
	 * @return
	 * @throws MalformedURLException
	 */
	private static DesiredCapabilities sauceConfigTemplateChrome(
			String platform, String version) throws MalformedURLException {
		caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", platform);
		caps.setCapability("version", version);
		caps.setCapability("name", testName);
		caps.setCapability("time-zone", "New_York");
		caps.setCapability("build", buildNumber);
		driver = new RemoteWebDriver(new URL(sauceURL), caps);
		return caps;
	}

	/**
	 * Description: Use this method when setting up Internet
	 *         Explorer browser environment in saucelabs
	 * @author mmuntakim 
	 * @param platform
	 * @param version
	 * @return
	 * @throws MalformedURLException
	 */
	private static DesiredCapabilities sauceConfigTemplateIE(String platform,
			String version) throws MalformedURLException {
		caps = DesiredCapabilities.internetExplorer();
		caps.setCapability("platform", platform);
		caps.setCapability("version", version);
		caps.setCapability("name", testName);
		caps.setCapability("time-zone", "New_York");
		caps.setCapability("build", buildNumber);
		driver = new RemoteWebDriver(new URL(sauceURL), caps);
		return caps;
	}

	/**
	 * Description: Use this method when setting up firefox
	 *         browser environment in saucelabs
	 * @author mmuntakim 
	 * @param platform
	 * @param version
	 * @return
	 * @throws MalformedURLException
	 */

	private static DesiredCapabilities sauceConfigTemplateFirefox(
			String platform, String version) throws MalformedURLException {
		caps = DesiredCapabilities.firefox();
		caps.setCapability("platform", platform);
		caps.setCapability("version", version);
		caps.setCapability("name", testName);
		caps.setCapability("time-zone", "New_York");
		caps.setCapability("build", buildNumber);
		driver = new RemoteWebDriver(new URL(sauceURL), caps);
		return caps;
	}

	/**
	 * Description: This method will set specified URl from
	 *         Settings class
	 * @author mmuntakim 
	 */
	public static void getURL() {
		driver.get(Settings.getUrl());
	}

	/**
	 * Description: This method will close all browsers and
	 *         driver from web or mobile
	 * @author mmmuntakim 
	 */
	public static void tearDownDriver() {
		WebDriverBasePage base = new WebDriverBasePage();
		if (Settings.getEnvironment().equalsIgnoreCase("android")
				|| Settings.getEnvironment().contains("iphone")) {
			AppiumDriver appiumDriver = (AppiumDriver) driver;
			appiumDriver.quit();
		} else {
			driver.close();
			driver.quit();
		}
	}

	/**
	 * Description: It will capture screenshot and save it locally in
	 *            project directory with the method name where screenshot is
	 *            taken
	 * @author mmuntakim 
	 * @param driver
	 * @param screenShotName
	 * @return
	 */
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

	/**
	 * Description: it displays skipped test during execution
	 *         in Report
	 * @author mmuntakim 
	 * @param result
	 */
	@AfterMethod(enabled = false)
	public void handlesSkippedTest(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			String skip = extentTest.getRunStatus().toString();
			extentTest.log(LogStatus.SKIP,
					"Skipped Tests name: " + result.getName(), skip);
			Report.endTheTest(extentTest);
		}
	}

	/**
	 * Description: This method used before test starts where
	 *         user must specify the test name and test group that test case
	 *         belongs to
	 * @author mmuntakim 
	 * @param enterTestName
	 * @param enterTestGroup
	 * @return
	 */
	public static ExtentTest startTest(String enterTestName,
			String enterTestGroup) {
		extentTest = Report.startTest(browserName + enterTestName)
				.assignCategory(enterTestGroup);
		return extentTest;

	}

	/**
	 * Description: This method is used after every test which
	 *         will end the test with Pass status along with specified message
	 * @author mmuntakim 
	 * @param enterMessage
	 */
	public static void endTestWithPassStatus(String enterMessage) {
		extentTest.log(LogStatus.PASS, browserName + enterMessage);
		Report.endTheTest(extentTest);
	}

	/**
	 * Description: The method is used where warning needs be
	 *         displayed during test execution
	 * @author mmmuntakim 
	 * @param enterMessage
	 */
	public static void getWarning(String enterMessage) {
		extentTest.log(LogStatus.WARNING, enterMessage);
	}

	/**
	 * Description: This method is used is each Test step to
	 *         specify the step action or information with pass status
	 * @author mmuntakim 
	 * @param enterTestStep
	 */
	public static void getTestStepName(String enterTestStep) {
		extentTest.log(LogStatus.PASS, enterTestStep);
	}

	/**
	 * Description: This method is used is each Test step to
	 *         specify the step action or information with info status
	 * @author mmuntakim 
	 * @param enterTestStep
	 */

	public static void getTestInfo(String enterTestInfo) {
		extentTest.log(LogStatus.INFO, enterTestInfo);
	}

}
