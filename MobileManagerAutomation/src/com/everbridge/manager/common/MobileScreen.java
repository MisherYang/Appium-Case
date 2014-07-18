package com.everbridge.manager.common;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Touch The Mobile screen
 * 
 * @author Misher Yang
 * @date 2014-5-23
 */

public class MobileScreen {

	public static void scroll(String direction, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", direction);
		js.executeScript("mobile: scroll", scrollObject);
	}

	public static void swipeScreen(Double startX, Double startY, Double endX,
			Double endY, Double duration, int repeat, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObj = new HashMap<String, Double>();
		swipeObj.put("startX", startX);
		swipeObj.put("startY", startY);
		swipeObj.put("endX", endX);
		swipeObj.put("endY", endY);
		swipeObj.put("duration", duration);
		for (int i = 0; i < repeat; i++) {
			try {
				js.executeScript("mobile: swipe", swipeObj);
			} catch (WebDriverException ex) {

				System.out.println("..........Touch Error!!!!!");
			}
		}

	}

	public static void clickScreen(Double x, Double y, Double duration,
			WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> tapObject = new HashMap<String, Double>();
		tapObject.put("x", x);
		tapObject.put("y", y);
		tapObject.put("duration", duration);
		js.executeScript("mobile: tap", tapObject);
	}

	// Home keycode: 3 Back keycode: 4 menu keycode: 82
	public static void putKeyCode(int keycode,WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Integer> keycodeObj = new HashMap<String, Integer>();

		keycodeObj.put("keycode", keycode);
		js.executeScript("mobile: keyevent", keycodeObj);	
//			
//		HashMap<String, Integer> keycode = new HashMap<String, Integer>();
//		keycode.put("keycode", 82);
//		((JavascriptExecutor)driver).executeScript("mobile: keyevent", keycode);
		
		


	}

}
