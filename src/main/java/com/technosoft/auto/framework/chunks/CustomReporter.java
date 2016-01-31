package com.technosoft.auto.framework.chunks;


import io.appium.java_client.ios.IOSDriver;


import java.util.List;
import java.util.Map;


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;


public class CustomReporter implements IReporter, ITestListener, ISuiteListener,
IInvokedMethodListener  {
	
	@SuppressWarnings("rawtypes")
	IOSDriver driver;


  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
      String outputDirectory) {
    //Iterating over each suite included in the test
    for (ISuite suite : suites) {
      //Following code gets the suite name
      String suiteName = suite.getName();
      //Getting the results for the said suite
      Map<String, ISuiteResult> suiteResults = suite.getResults();
      for (ISuiteResult sr : suiteResults.values()) {
        ITestContext tc = sr.getTestContext();
        Reporter.log("Passed tests for suite '" + suiteName +
 "' is:" + tc.getPassedTests().getAllResults().size());
        Reporter.log("Failed tests for suite '" + suiteName +
 "' is:" + 
 tc.getFailedTests().getAllResults().size());
        Reporter.log("Skipped tests for suite '" + suiteName +
 "' is:" + 
 tc.getSkippedTests().getAllResults().size());
      }
    }
  }

  @Override
	public void onStart(ISuite arg0) {
		Reporter.log("onStart Suite - About to begin executing Suite " + arg0.getName(), true);
	}

	// Belongs to ISuiteListener - it executes once the Suite is finished
	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("onFinish Suite - About to end executing Suite " + arg0.getName(), true);
	}

	// Belongs to ITestListener - it executes before starting of set of Tests
	public void onStart(ITestContext arg0) {
		Reporter.log("onStart Test - About to begin executing Test set " + arg0.getName(), true);
	}

	// Belongs to ITestListener - it executes once set of Tests is finished
	public void onFinish(ITestContext arg0) {
		Reporter.log("onFinish Test - Completed executing Test set " + arg0.getName(), true);
	}

	// Belongs to ITestListener - it executes only when the test is pass
	public void onTestSuccess(ITestResult arg0) {
		// This is calling the printTestResults method
	}

	// Belongs to ITestListener - it executes only when the test fail
	// (@Test)
	public void onTestFailure(ITestResult arg0) {
		// This is calling the printTestResults method
			printTestResults(arg0);
			
			
			
			
	
	}

	// Belongs to ITestListener - it executes before the test (@Test) start
	public void onTestStart(ITestResult arg0) {
		System.out.println("onTestStart - The execution of the main test starts now");
	}

	// Belongs to ITestListener - it executes only if test (@Test) is skipped
	public void onTestSkipped(ITestResult arg0) {
		printTestResults(arg0);
	}

	// This is the method which will be executed in case of test pass or fail
	// This will provide the information on the test
	private void printTestResults(ITestResult result) {
		Reporter.log("Test Method resides in "
				+ result.getTestClass().getName(), true);

		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		Reporter.log("Test Status: " + status, true);
	}

	// Belongs to IInvokedMethodListener - it executes before every
	// method including @Before @After @Test
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "beforeInvocation - About to begin executing following method : "
				+ returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
	}

	// Belongs to IInvokedMethodListener - it executes after every
	// method including @Before @After @Test
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "afterIncovation - Completed executing following method : "
				+ returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
	}

	// This will return method names to the calling function
	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "."
				+ method.getMethodName();
	}
	// Ignore this
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	
	// This Method will take screenshot only on Failure



}