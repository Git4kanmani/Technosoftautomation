package com.technosoft.auto.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SampleIosTest {
	 static AppiumDriverLocalService service = null;
	
	 static String appiumInstallationDir = "/Applications";// e.g. for Mac

	public static void main(String[] args) throws Exception {
		
//		DesiredCapabilities cap = new DesiredCapabilities();
//		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
//		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,
//				MobilePlatform.IOS);
//		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
//		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
//		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
//		@SuppressWarnings("rawtypes")
//		IOSDriver driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"),
//				cap);
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		driver.get("http://google.com");
//		Thread.sleep(4000);
//		driver.quit();
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,
				MobilePlatform.ANDROID);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.2");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "TestDevice");
		cap.setCapability("appPackage","com.android.chrome");
		cap.setCapability("appActivity","Main");
		@SuppressWarnings("rawtypes")
		AndroidDriver driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),
				cap);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://google.com");
		Thread.sleep(4000);
		driver.quit();
	
	}

	// Appium Launch on Mac
	public static void startAppiumonMac() throws Exception {
//		// node appium
//		// node appium.js
//		CommandLine command = new CommandLine(
//				"/Applications/Appium.app/Contents/Resources/node/bin/node");
//		command.addArgument(
//				"/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js",
//				false);
//		command.addArgument("--address", false);
//		command.addArgument("0.0.0.0");
//		command.addArgument("--port", false);
//		command.addArgument("4723");
//		command.addArgument("--no-reset", false);
//		command.addArguments(GeneralServerFlag.DEVICE_NAME,"");
//		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
//		DefaultExecutor executor = new DefaultExecutor();
//		executor.setExitValue(1);
//		executor.execute(command, resultHandler);
//		Thread.sleep(3000);
		
	
	}

	// kill all nodes
	public static void stopAppium() throws Exception {
		Runtime.getRuntime().exec("killall node");
	}

}
