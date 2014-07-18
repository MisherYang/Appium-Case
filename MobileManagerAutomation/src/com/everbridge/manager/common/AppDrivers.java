package com.everbridge.manager.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;


/**
 * @ClassName DriverManager
 * @Description TODO
 * @author Misher Yang
 * @date 6 3, 2014
 */

public class AppDrivers {

	private static WebDriver driver = null;
	private static DesiredCapabilities capability;

	public static void newDriver() {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/apps/MobileManagerApp");
//		driver.isAppInstalled("com.example.android.apis");
//		driver.lauchApp
		File app = new File(appDir, "Manager_QA_31_1093_051214.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "Android");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, "4.4.2");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("app-package",
				"net.everbridge");
		capabilities.setCapability("app-activity", ".ui.SplashUI");
		try {
			driver = new SwipeableWebDriver(new URL(
					"http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			newDriver();
		}
		return driver;
	}

	public static void closeDriver() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	
	public static void newManager(){
		

//		public AppiumDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("appPackage", "com.everbridge.mobile.iv.recipient");
        capabilities.setCapability("appActivity", ".SplashActivity");
//        appium的独特地方,使用自己的AppiumDriver, 它本身是继承自WebDriver
    	try {
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        //设置超时
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        

		
		
	}
}
