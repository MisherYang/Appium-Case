
package com.everbridge.manager.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.everbridge.manager.common.AppDrivers;
import com.everbridge.manager.common.MobileScreen;

/**
 * Msg list page, including Menu.
 * 
 * @author Misher Yang
 * @date 2014-5-21
 */

public class MsgListPage {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public MsgListPage() {
		this.driver = AppDrivers.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/ib_open_sidebar")
	public WebElement menuBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/txt_filter")
	public WebElement filterText;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_create_message")
	public WebElement unsolicitedBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/listView")
	public List<WebElement> msgList;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/empty")
	public WebElement emptyPagePrompt;

	// This menu doesn't contain "Application Passcode"
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/txt_account")
	public List<WebElement> menu;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/layout_settings")
	public WebElement settingsLayout;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/passcode_layout")
	public WebElement passcodeLayout;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/layout_about")
	public WebElement aboutLayout;

	// The text is "Loading, please wait …"
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/head_tipsTextView")
	public WebElement headLoadTip;

	public String loadElementId = "com.everbridge.mobile.iv.recipient:id/head_tipsTextView";
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/head_lastUpdatedTextView")
	public WebElement lastUpdatedTime;


	public void clickMenu() {
		menuBtn.click();
	}

	public void clickUnsolicitedMsg() {
		unsolicitedBtn.click();
	}

	public void clickFirstMsgInList() {
		unsolicitedBtn.click();
	}

	
	public int addedMembersNo(){
		clickMenu();
		int no=menu.size() -3;
		MobileScreen.swipeScreen(935.0, 603.0, 237.0, 603.0, 0.5, 1, driver);
		return no;
	}
	
	
	public boolean isMemberAdded(String memberName) {
		boolean hasMember = false;
		clickMenu();
		System.out.println(".....................Open Menu....!!!!!");
		int addedMembers = menu.size() - 3;
		for (int i = 0; i <= addedMembers; i++) {
			if (menu.get(i).getText().equals(memberName)) {
				hasMember = true;
				System.out.println("........................The member has been added: "+ memberName);
			}
		}
		MobileScreen.swipeScreen(935.0, 603.0, 237.0, 603.0, 0.5, 1,driver);
		System.out.println("......................Close Menu。。。。!!!!!");
		return hasMember;
	}

}
