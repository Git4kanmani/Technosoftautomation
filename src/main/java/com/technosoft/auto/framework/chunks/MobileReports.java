package com.technosoft.auto.framework.chunks;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class MobileReports {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {

		extent = new ExtentReports("Extent_Reports/testExtend.html", true);
		extent.config().documentTitle("Automation Report")
				.reportName("RFR Sanity ")
				.reportHeadline("Report for Build # 001");
		extent.addSystemInfo("Appium Version", "3.2.0").addSystemInfo(
				"Environment", "Sandbox");
		return extent;
	}

	public static void endTheTest(ExtentTest enterTest) {
		extent.endTest(enterTest);
	}

	public static void flush() {
		extent.flush();
	}
	
	public static void testSuiteCleanUp() {
		extent.close();
	}

	public static ExtentTest startTest(String enterTestName) {
		ExtentTest testName = extent.startTest(enterTestName);
		return testName;
	}
	
}
