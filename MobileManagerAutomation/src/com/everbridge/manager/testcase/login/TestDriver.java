package com.everbridge.manager.testcase.login;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.everbridge.manager.common.AppDrivers;
import com.everbridge.manager.common.SwipeableWebDriver;

public class TestDriver {

	
	
	
	  private AppiumDriver driver;
	
	@Test
	public void testtest() throws InterruptedException {
		
     if( driver.isAppInstalled("com.everbridge.mobile.iv.recipient")){

		System.out.println("..................................................");}
     else{
    	 
     }
		
     System.out.println(".....................!!!!!!!!!!!.............................");
	}
	
}
