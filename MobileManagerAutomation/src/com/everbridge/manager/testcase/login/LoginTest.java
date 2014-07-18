package com.everbridge.manager.testcase.login;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.everbridge.manager.common.GetConfElements;
import com.everbridge.manager.common.TestData;
import com.everbridge.manager.pages.LoginPage;
import com.everbridge.manager.pages.MsgListPage;

/**
 * Install Member App, and add the first member.
 * @ClassName LoginTest
 * @author Misher
 * @date May 6, 2014
 */

 public class LoginTest {

	String propertiesFile = GetConfElements.datafile_name;
	Map<String, String> testData = TestData
			.parseTestData(propertiesFile);
	
	@Test
	public void loginFor1stTime() throws InterruptedException {

		Thread.sleep(1000);
		LoginPage lp = new LoginPage();
		Thread.sleep(2000);
		System.out.println(".....................Open the login page...");

		lp.inputUserName(testData.get("username1_value"));
		lp.inputPassword(testData.get("password1_value"));
		lp.clickLoginBtn();
		// Thread.sleep(30000);
//     try{
//		new WebDriverWait(lp.getDriver(), 60).until(
//				(ExpectedConditions
//						.visibilityOf(lp.doneBtn)));
//        }
//			catch(NoSuchElementException Ex){
//			System.out.println(".....................Login Failed!!!!!");
//		}			
//		
//		
//		lp.clickDoneBtn();
//		System.out.println(".....................Login successfully");
//
//
//		MsgListPage ms = new MsgListPage();		
//		new WebDriverWait(lp.getDriver(), 300).until(
//				(ExpectedConditions.invisibilityOfElementLocated(By.id(ms.loadElementId))
//						));	
//		Assert.assertTrue(ms.isMemberAdded(testData.get("username1_value")));

	}

}
