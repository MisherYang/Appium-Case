package com.everbridge.manager.testcase.roles;

import java.util.Map;

import org.testng.annotations.Test;

import com.everbridge.manager.common.GetConfElements;
import com.everbridge.manager.common.TestData;
import com.everbridge.manager.pages.LoginPage;

public class AccountAdminTest {

	
	String propertiesFile = GetConfElements.datafile_name;
	Map<String, String> testData = TestData
			.parseTestData(propertiesFile);
	
	@Test
	public void accountAdminTest() throws InterruptedException {
		Thread.sleep(1000);
		LoginPage lp = new LoginPage();
		lp.login(testData.get("accountadmin_username"), testData.get("accountadmin_pw"));
		
		
		
	}
	
	
}
