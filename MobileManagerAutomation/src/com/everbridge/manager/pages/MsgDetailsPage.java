package com.everbridge.manager.pages;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everbridge.manager.common.AppDrivers;

/**
 * Common class for Mobile Member, including msg details Page and method
 * 
 * @ClassName MsgDetailsPage
 * @Description TODO
 * @author Misher
 * @date May 20, 2014
 */

public class MsgDetailsPage {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public MsgDetailsPage() {
		this.driver = AppDrivers.getDriver();
		PageFactory.initElements(driver, this);
	}

	// The loading Text is: "Loading, please wait …"
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/head_tipsTextView")
	public WebElement loadingDetailsPage;

	// Confirm Dialog
	@FindBy(id = "android:id/message")
	public WebElement confirmText;

	@FindBy(id = "android:id/button1")
	public WebElement confirmYes;

	@FindBy(id = "android:id/button2")
	public WebElement confirmCancel;

	// Msg Details
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_left_action_tv")
	public WebElement backBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_right_action_tv")
	public WebElement shareMsgBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_confirm")
	public WebElement confirmBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_from_tv")
	public WebElement msgFrom;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_to_tv")
	public WebElement msgTo;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/txt_msg_title")
	public WebElement msgTitle;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/content_tv")
	public WebElement msgContent;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_image")
	public WebElement msgImage;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_audio")
	public WebElement msgAudio;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/message_attachment")
	public WebElement msgAttach;

	// --------Preview Image Page ---------------------------------
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/top_title_tv")
	public WebElement imagePreviewTitle;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/msg_detail_gridview")
	public List<WebElement> imageList;

	// FeedBack Box
	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/edt_comments")
	public WebElement feedbackCommentEdt;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_carmera")
	public WebElement feedbackImageBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_location")
	public WebElement feedbackLocationBtn;

	@FindBy(id = "com.everbridge.mobile.iv.recipient:id/btn_send")
	public WebElement feedbackSendBtn;

	public boolean isConfirmDisplay() {
		return confirmYes.isDisplayed();
	}

	public boolean isReplyBoxDisplay() {
		return confirmYes.isDisplayed();
	}

	public void checkFeedbackisEnabled(boolean imageBtn, boolean locationBtn,
			boolean commentBtn) {
		Assert.assertEquals(imageBtn, feedbackImageBtn.isEnabled());
		Assert.assertEquals(locationBtn, feedbackLocationBtn.isEnabled());
		Assert.assertEquals(imageBtn, feedbackCommentEdt.isEnabled());
		System.out.println("......................The message [ "
				+ msgTitle.getText() + " ] feedback box is: Image Button ->"
				+ feedbackImageBtn.isEnabled() + "; Location Button ->"
				+ feedbackLocationBtn.isEnabled() + "; Comment EditBox ->"
				+ feedbackCommentEdt.isEnabled());
	}

}
