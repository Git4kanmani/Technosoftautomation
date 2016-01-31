package com.technosoft.auto.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.technosoft.auto.framework.chunks.Settings;
import com.technosoft.auto.framework.chunks.WebDriverBasePage;
import com.technosoft.auto.framework.locators.Locators.HomeScreen;
import com.technosoft.auto.framework.testdata.TestConstants;
import com.technosoft.auto.framework.testdata.TestConstants.TestData;

public class SamplePage extends WebDriverBasePage {

	public static void loginButton() {
		clickOnElement("xpath", HomeScreen.LOGIN_BUTTON,
				"Clicked on Login button", "Yes",
				"Unable to click on button field");
	
	}

	public static void enterDataIntoEmailField(String enterValue) {
		if (Settings.getEnvironment().equals("iphone")) {
			// Code here will only execute on iphone
		} else if (Settings.getEnvironment().equals("Android")) {
			// Code here will only execute on Android
		} else {
			enterTextintoInputField("id", HomeScreen.EMAIL_FIELD, enterValue,
					"Email Address entered", "yes",
					"Unable to enter email on email field");
		}
	}

}
