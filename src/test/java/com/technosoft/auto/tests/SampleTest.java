package com.technosoft.auto.tests;


import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.technosoft.auto.framework.chunks.WebDriverActivities;
import com.technosoft.auto.framework.pages.SamplePage;
import com.technosoft.auto.framework.testdata.TestConstants.TestData;

public class SampleTest extends WebDriverActivities{
	
	
	@Test(enabled = true)
	public static void sampleTestCase() {
		startTest("Sample Test", "Regression Group");
		SamplePage.enterDataIntoEmailField(TestData.EMAIL_FIELD_DATA);
		SamplePage.loginButton();
		endTestWithPassStatus("Sample  Test");
	}
	

	@AfterClass
	public static void afterClass() {
		tearDownDriver();
	}
	
	@Test
	public static void gitHubTest() {
		System.out.println("This is a test from Mohammad");
		System.out.println("This is Saki");
	}

}
