package com.everbridge.manager.common;

import org.testng.annotations.AfterSuite;

public class CloseDrivers {

	@AfterSuite
	public void afterSuite() {
		AppDrivers.closeDriver();
	}

}
