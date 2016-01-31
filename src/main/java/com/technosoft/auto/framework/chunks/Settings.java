package com.technosoft.auto.framework.chunks;

public class Settings {

	/*
	 * Please follow below steps before Running the Test 1) Decide Which test to
	 * run either Mobile or Web 2) Specify the XML which you want run in POM.XML
	 * in project directory 3) If you running Mobile Test Please do Following A)
	 * webApplicationRun field must be set to "false" B) Mobile field must be
	 * set to "true" C) Make sure to start Appium Server D) Navigate to you
	 * project in Terminal and Type "mvn test", which will start running the
	 * test 4) If you running widget Test please do following A) widgetRun field
	 * must be set to "true" B) rfrRun field must be set to "false" C) set
	 * enviornemnt field as per below logic If you set value as "Android", it
	 * will run the test on andorid specified enviornment If you set value as
	 * "iphone", it will run the test on ios device If you set value as "web",
	 * it will run the test on web with widget being on Frame D) Goto
	 * RTM_Widget_Functionality class and make sure all test are set to enable
	 * F) Just verify if envUrl Specifed to correct environment for example:
	 * int, sandbox, demo, etc E) Make sure to update the URL on either urlFrame
	 * or originalUrl F) Make sure to start Appium Server if test running on
	 * Android or iOS, otherwise ignore this step0 G) Navigate to you project in
	 * Terminal and Type "mvn test", which will start running the test
	 */

	public static final boolean webApplicationRun = true;
	public static final boolean mobileRun = false;
	private static String setEnviornment = "web";
	private static String webUrl = "https://www.facebook.com";

	/**
	 * Description: This Method controls which environment test should be
	 * executed on, for example: web, iphone, android, etc
	 * 
	 * @author mmuntakim
	 * @return
	 */
	public static String getEnvironment() {
		return setEnviornment;
	}

	/**
	 * Description: This methods returns the URL the application should test on,
	 * based on webUrl field
	 * 
	 * @author mmuntakim
	 * @return
	 */
	public static String getUrl() {
		return webUrl;
	}

	/**
	 * Description: Specifies database credintails for login
	 * 
	 * @author mmuntakim
	 */

	public final class DbLoginCredintials {
		public final static String URL = "jdbc:mysql://localhost:3306/";
		public final static String DATABASE_NAME = "Database";
		public final static String USERNAME = "root";
		public final static String PASSWORD = "Mujabmo8690";
	}

}
