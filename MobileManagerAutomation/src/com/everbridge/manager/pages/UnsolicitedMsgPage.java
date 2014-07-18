package com.everbridge.manager.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everbridge.manager.common.AppDrivers;

public class UnsolicitedMsgPage {

	private WebDriver driver;
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public UnsolicitedMsgPage(){
		this.driver = AppDrivers.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_left_action_tv")
	public WebElement backBtn;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_right_action_tv")
	public WebElement sendBtn;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/your_message_sent_to")
	public WebElement msgSendTo;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/title_category")
	public WebElement msgCategory;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/edt_title")
	public WebElement edtTitle;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/edt_body")
	public WebElement edtBody;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_carmera")
	public WebElement cameraBtn;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_location")
	public WebElement locationBtn;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/layout_location_button")
	public WebElement locationLoadingIcon;
	
	//The Sent Msg Details Page
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/txt_msg_title")
	public WebElement msgTitleSend;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_to_tv")
	public WebElement orgNameSend;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_right_action_tv")
	public WebElement shareBtnSend;
	
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/location_iv")
	public WebElement locationIconSend;
	
	public void clickCategory(){
		msgCategory.click();
	}
	
	public void clickImageButton(){
		cameraBtn.click();
	}
	
	public void clickLocationButton(){
		locationBtn.click();
	}
	
	public void clickSendButton(){
		sendBtn.click();
	}
}
