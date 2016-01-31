package com.technosoft.auto.framework.chunks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

/**
 * Description: This class resides all action which user will perform on web
 * application
 * 
 * @author mmuntakim
 *
 */

public class WebDriverBasePage extends WebDriverActivities {

	private static Select dropdown = null;
	private boolean enabled = false;

	public String getTagName() {
		return "tagName";
	}

	public String getXpath() {
		return "xpath";
	}

	public String getYes() {
		return "yes";
	}

	public String getNo() {
		return "no";
	}

	/**
	 * Description: Returns WebElements value based on specified locators
	 * 
	 * @author mmuntakim
	 * @param locator
	 * @param locatorValue
	 * @return
	 */
	public static WebElement getElement(String locator, String locatorValue) {

		if (locator.equalsIgnoreCase("id")) {
			return driver.findElement(By.id(locatorValue));
		} else if (locator.equalsIgnoreCase("className")) {
			return driver.findElement(By.className(locatorValue));
		} else if (locator.equalsIgnoreCase("name")) {
			return driver.findElement(By.name(locatorValue));
		} else if (locator.equalsIgnoreCase("xpath")) {
			return driver.findElement(By.xpath(locatorValue));
		} else if (locator.equalsIgnoreCase("css")) {
			return driver.findElement(By.cssSelector(locatorValue));
		} else if (locator.equalsIgnoreCase("tagName")) {
			return driver.findElement(By.tagName(locatorValue));
		} else
			throw new NoSuchElementException("No Such Element : "
					+ locatorValue);
	}

	/**
	 * Description: If application or web page has Frame, user must go to
	 * specific frame before performing any actions, This method will allow user
	 * to switch to that frmme by simply entering "frameid from DOM"
	 * 
	 * @author mmuntakim
	 * @param enterFrameID
	 */
	public static void switchToFrame(String enterFrameID) {

		if (Settings.getEnvironment().equalsIgnoreCase("WebDefault")) {
			getTestStepName("Default URL Without frame");
		} else {
			WebElement frameID = driver.findElement(By.id(enterFrameID));
			driver.switchTo().frame(frameID);
		}
	}

	/**
	 * Description: This method will return user to default view from FrameID
	 * 
	 * @author mmuntakim
	 */

	public static void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Description: This method controls showstopper scenario, 1) if test step
	 * fails and if its a blocker step then test will terminate and move to next
	 * test script. 2) If test step fails and its not a blocker, then test will
	 * throw warnning on report with details but will continue with next test
	 * step
	 * 
	 * @author mmuntakim
	 * @param showStopper
	 * @param exceptionMessage
	 */

	public static void getShowStopperStatus(String showStopper,
			String exceptionMessage) {

		String blocker = showStopper;

		if (blocker.equalsIgnoreCase("yes")) {
			getURL();
			getWarning("Exception Details: "
					+ new NoSuchElementException(exceptionMessage));
			throw new NoSuchElementException(exceptionMessage);
		} else if (blocker.equalsIgnoreCase("no")) {
			getWarning("Exception Details: "
					+ new NoSuchElementException(exceptionMessage));
			Reporter.log(exceptionMessage);

		}

	}

	/**
	 * Description: Call this method on POM classes when user needs to click on
	 * any elements
	 * 
	 * @author mohammadmuntakim1
	 * @param locator
	 * @param locatorValue
	 * @param enterInfo
	 * @param blocker
	 * @param errorMessage
	 */

	public static void clickOnElement(String locator, String locatorValue,
			String enterInfo, String blocker, String errorMessage) {
		try {
			getElement(locator, locatorValue).click();
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);

		}
	}

	/*
	 * This method is called on all POM page when user needs to enter data into
	 * text field
	 */
	public static void enterTextintoInputField(String locator, String enterLocatorvalue,
			String enterValue, String enterInfo, String blocker,
			String errorMessage) {
		try {
			getElement(locator, enterLocatorvalue).sendKeys(enterValue);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	/*
	 * This method is called on all POM page when user needs to clear data from
	 * text field
	 */

	public void clearTextField(String enterLocatorvalue, String enterInfo,
			String blocker, String errorMessage) {
		try {
			driver.findElement(By.xpath(enterLocatorvalue)).clear();
			;
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	/*
	 * Return text for specified Web Elements
	 */
	public String verifyText(String locator, String locatorValue,
			String enterInfo, String blocker, String errorMessage) {
		String text = null;
		try {
			text = getElement(locator, locatorValue).getText();
			getTestStepName(enterInfo);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
		return text;
	}

	public void verifyDefaultValueOfDropDown(String enterXpath,
			String expected, String enterInfo, String errorMessage,
			String blocker) {
		try {
			Select option = new Select(driver.findElement(By.xpath(enterXpath)));
			String defaultoption = option.getFirstSelectedOption().getText();
			defaultoption.contains(expected);
			// Assert(expected, defaultoption);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | AssertionError e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
	}

	public void selectValueByVisibleText(String enterXpath, String enterValue,
			String enterInfo, String blocker, String errorMessage) {
		try {
			Select option = new Select(driver.findElement(By.xpath(enterXpath)));
			option.selectByVisibleText(enterValue);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | AssertionError e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
	}

	public void selectValueByIndex(String enterXpath, int enterValue,
			String enterInfo, String blocker, String errorMessage) {
		try {
			Select option = new Select(driver.findElement(By.xpath(enterXpath)));
			option.selectByIndex(enterValue);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | AssertionError e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
	}

	/*
	 * Switches window to child view
	 */
	public void switchTo(int index) {
		try {
			List<String> listOfWindows = new ArrayList<String>(
					driver.getWindowHandles());
			System.out.println("List of windows: " + listOfWindows);
			for (String window : listOfWindows) {
			}
			driver.switchTo().window(listOfWindows.get(index));
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Invalid Window Index : "
					+ index);
		}

	}

	/*
	 * Switches window to default or parent view
	 */

	public void switchToRootWindowAndCloseCurrentBrowser() {
		List<String> listOfWindows = new ArrayList<String>(
				driver.getWindowHandles());

		for (int i = 1; i < listOfWindows.size(); i++) {
			driver.switchTo().window(listOfWindows.get(i));
			driver.close();
		}
		driver.switchTo().window(listOfWindows.get(0));

	}

	/*
	 * Verifies page title
	 */

	public void verifyTitle(String enterTitle, String enterInfo,
			String blocker, String errorMessage) {
		try {
			driver.getTitle().equals(enterTitle);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);

		}
	}

	/*
	 * Selected dropdown values by index
	 */
	public void select(String locator, int index, String locatorValue,
			String enterInfo, String blocker, String errorMessage) {
		try {
			dropdown = new Select(getElement(locator, locatorValue));
			dropdown.selectByIndex(index);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
	}

	/*
	 * Selcted dropdown values by value
	 */

	public void select(String locator, String enterValue, String locatorValue,
			String enterInfo, String blocker, String errorMessage) {
		try {
			dropdown = new Select(getElement(locator, locatorValue));
			dropdown.selectByValue(enterValue);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
	}

	/*
	 * Selects dropdown value by visible text
	 */

	public void selecteByVisibleText(String locator, String locatorValue,
			String enterText, String enterInfo, String blocker,
			String errorMessage) {
		try {
			dropdown = new Select(getElement(locator, locatorValue));
			dropdown.selectByVisibleText(enterText);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}

	}

	/*
	 * Asserts the entire dropdown list and clicks on the value which is
	 * specified
	 */

	public void selectValueFromList(String xpathValue,
			String enterDropDownValue, String enterInfo, String blocker,
			String errorMessage) {
		List<WebElement> element = null;
		try {
			element = driver.findElements(By.xpath(xpathValue));
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
		for (WebElement elements : element) {
			String dropDownValue = elements.getText();
			// System.out.println("Dropdown Value: "+ dropDownValue);
			if (dropDownValue.equals(enterDropDownValue)) {

				elements.click();
				getTestStepName(enterInfo);
				break;
			} else {
				System.out.println("Values of Dropdown: " + dropDownValue);
			}
		}
	}

	/*
	 * Asserts list from web pages with actual and expected arrays
	 */
	public void assertValueFromList(String xpathValue, String[] expected,
			String enterInfo, String blocker, String errorMessage) {
		List<WebElement> element = null;
		try {
			element = driver.findElements(By.xpath(xpathValue));
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
		if (expected.length != element.size()) {
			getWarning("Fail wrong number of elements found: " + enterInfo);
		}

		for (int i = 0; i < expected.length; i++) {
			String actualValues = element.get(i).getText();
			if (actualValues.equals(expected[i])) {
				getTestStepName("Passed on: " + actualValues);

			} else {
				getWarning("Expected value: " + expected[i] + "===="
						+ "Actual value: " + actualValues);
			}
		}

	}

	/*
	 * Verifies expected vs actual result
	 */
	public void assertText(String locatorValue, String enterInfo,
			String blocker, String errorMessage, String expectedText) {
		// String text = verifyText(getXpath(), locatorValue, enterInfo,
		// blocker, errorMessage);

		String text;
		try {
			text = driver.findElement(By.xpath(locatorValue)).getText();
			Assert.assertEquals(errorMessage, expectedText, text);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException | AssertionError e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	/*
	 * Verifies if element is disabled
	 */
	public void isDisableElement(String enterXpath, String enterInfo,
			String blocker, String errorMessage) {

		try {
			enabled = driver.findElement(By.xpath(enterXpath)).isEnabled();
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
			getShowStopperStatus(blocker, errorMessage);
		}
		if (enabled == true) {
			getWarning("Result displays as Enabled: " + enterInfo + " : "
					+ enabled);
		} else if (enabled == false) {
			getTestStepName("Result displays as Disabled: " + enterInfo + " : "
					+ enabled);
		}
	}

	/*
	 * Verifies font color of an Web Element
	 */
	public void verifyFontColor(String enterXpath, String enterInfo,
			String errorMessage, String expectedText, String blocker) {
		try {
			String fontColor = driver.findElement(By.xpath(enterXpath))
					.getCssValue("color");
			Assert.assertEquals(errorMessage, expectedText, fontColor);
			getTestStepName(enterInfo);
			System.out.println("Font Color: " + fontColor);
		} catch (NoSuchElementException | TimeoutException | AssertionError e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	public void tapOnElement(int x, int y, String enterInfo)
			throws InterruptedException {
		HashMap<String, Integer> tapObject = new HashMap<String, Integer>();
		tapObject.put("x", x); // in pixels from left
		tapObject.put("y", y); // in pixels from top
		Thread.sleep(5000);
		((RemoteWebDriver) driver).executeScript("mobile: tap", tapObject);
		Thread.sleep(2000);
		getTestStepName(enterInfo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void selectMobileElementByText(String enterClassname,
			String valueToBeSelected, String enterInfo, String blocker,
			String errorMessage) throws InterruptedException {
		AppiumDriver appiumDriver = (AppiumDriver) driver;
		appiumDriver.context("NATIVE_APP");
		try {
			List<WebElement> elements = appiumDriver.findElements(MobileBy
					.className(enterClassname));
			for (WebElement element : elements) {
				// System.out.println("Text====: "+ element.getText());
				if (element.getText().equalsIgnoreCase(valueToBeSelected)) {
					element.click();
					break;
				}
			}
			getTestStepName(enterInfo);
			WebDriverActivities.switchToWebview();
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void selectMobileElementByIndex(String enterClassname,
			int enterIndexValue, String enterInfo, String blocker,
			String errorMessage) throws InterruptedException {
		AppiumDriver appiumDriver = (AppiumDriver) driver;
		appiumDriver.context("NATIVE_APP");
		try {
			List<WebElement> elements = appiumDriver.findElements(MobileBy
					.className(enterClassname));
			MobileElement index = (MobileElement) elements.get(enterIndexValue);
			index.click();
			getTestStepName(enterInfo);
			WebDriverActivities.switchToWebview();
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void swipeDownOnMobileScreen(int startX, int startY, int endX,
			int endY, String enterInfo) throws InterruptedException {
		AppiumDriver appiumDriver = (AppiumDriver) driver;
		appiumDriver.context("NATIVE_APP");
		Thread.sleep(2000);
		int endx = endX;
		int startx = startX;
		int starty = startY;
		int endy = endY;
		appiumDriver.swipe(startx, starty, endx, endy, 1000);
		getTestStepName(enterInfo);
		WebDriverActivities.switchToWebview();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void scrollToMobileElementAndClickOnElement(String enterClassname,
			String valueToBeSelected, String enterInfo, String blocker,
			String errorMessage) throws InterruptedException {
		AppiumDriver appiumDriver = (AppiumDriver) driver;
		appiumDriver.context("NATIVE_APP");
		List<WebElement> elements = null;
		try {
			for (int i = 1; i < 3; i++) {
				elements = appiumDriver.findElements(MobileBy
						.className(enterClassname));
				for (WebElement element : elements) {
					// System.out.println("Element: " + element.getText());
					if (element.getText().equals(valueToBeSelected)) {
						element.click();
						break;
					}

				}
				swipeDownOnMobileScreen(536, 1792, 524, 240,
						"Swiped down on time screen");

			}
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(blocker, errorMessage);
			e.printStackTrace();
		}
	}

}
