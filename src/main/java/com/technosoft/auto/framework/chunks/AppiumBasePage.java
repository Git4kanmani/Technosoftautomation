package com.technosoft.auto.framework.chunks;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

public class AppiumBasePage extends AppiumDriverActivities {

	public String getYes() {
		return "yes";
	}

	public String getNo() {
		return "no";
	}

	/*
	 * Specifies show stopper step logic
	 */

	public void getShowStopperStatus(String showStopper, String errorMessage) {
		String blocker = showStopper;
		if (blocker.equalsIgnoreCase("yes")) {
			driver.resetApp();
			getWarning("Exception Details: "
					+ errorMessage);
			throw new NoSuchElementException(errorMessage);
		} else if (blocker.equalsIgnoreCase("no")) {
			getWarning("Exception Details: " +
					errorMessage);
			Reporter.log(errorMessage);
		}
	}

	@SuppressWarnings("unchecked")
	public void enterDataUsingClassIndex(String className, int enterIndex,
			String enterInfo, String showStopper, String errorMessage,
			String enterTextValue) {
		try {
			List<WebElement> element = driver.findElements(MobileBy
					.className(className));
			MobileElement index = (MobileElement) element.get(enterIndex);
			index.tap(1, 1);
			driver.getKeyboard().sendKeys(enterTextValue);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException
				| IndexOutOfBoundsException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void clickOnElementUsingNameIndex(String enterNameValue,
			int enterIndex, String enterInfo, String showStopper,
			String errorMessage) {
		try {
			List<WebElement> element = driver.findElements(MobileBy
					.name(enterNameValue));
			MobileElement index = (MobileElement) element.get(enterIndex);
			index.tap(1, 1);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException
				| IndexOutOfBoundsException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clickOnElementUsingClassNameIndex(String enterClassNameValue,
			int enterIndex, String enterInfo, String showStopper,
			String errorMessage) {
		try {
			List<WebElement> element = driver.findElements(MobileBy
					.name(enterClassNameValue));
			MobileElement index = (MobileElement) element.get(enterIndex);
			index.tap(1, 1);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException
				| IndexOutOfBoundsException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}

	public void tapOnButtonUsingName(String enterNameValue, String showStopper,
			String enterInfo, String errorMessage) {
		try {
			MobileElement btn = (MobileElement) driver
					.findElementByName(enterNameValue);
			btn.tap(1, 1);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}
	
	public void tapOnButtonUsingXpath(String enterxpathValue, String showStopper,
			String enterInfo, String errorMessage) {
		try {
			MobileElement btn = (MobileElement) driver
					.findElementByXPath(enterxpathValue);
			btn.tap(1, 1);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}

	public void verifyText(String enterNameValue, String expectedResult,
			String showStopper, String enterInfo, String errorMessage) {
		try {
			MobileElement attribute = (MobileElement) driver
					.findElement(MobileBy.name(enterNameValue));
			Assert.assertEquals(attribute.getAttribute("name"), expectedResult,
					errorMessage);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException | AssertionError e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}
	
	public void verifyTextUsingXpath(String enterXpathValue, String expectedResult,
			String showStopper, String enterInfo, String errorMessage) {
		try {
			MobileElement attribute = (MobileElement) driver
					.findElement(MobileBy.xpath(enterXpathValue));
			Assert.assertEquals(attribute.getAttribute("name"), expectedResult,
					errorMessage);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException | AssertionError e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void verifyTextUsingClassIndexValue(String enterClassValue, int enterIndex, String expectedResult,
			String showStopper, String enterInfo, String errorMessage) {
		try {
			List<WebElement> element = driver.findElements(MobileBy
					.className(enterClassValue));
			MobileElement index = (MobileElement) element.get(enterIndex);
			Assert.assertEquals(index.getAttribute("value"), expectedResult,
					errorMessage);
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException | AssertionError e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}

	public String getTextByXpath(String enterXpathValue, String enterInfo,
			String showStopper, String errorMessage) {
		String attribute = null;
		try {
			attribute = driver.findElement(MobileBy.xpath(enterXpathValue))
					.getText();
			getTestStepName(enterInfo);
		} catch (NoSuchElementException | TimeoutException e) {
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
		return attribute;
	}

	public  void assertValues(String actual, String expected, String enterInfo,
			String showStopper, String errorMessage) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			getTestStepName(enterInfo);
			getShowStopperStatus(showStopper, errorMessage);
			e.printStackTrace();
		}
	}

}
