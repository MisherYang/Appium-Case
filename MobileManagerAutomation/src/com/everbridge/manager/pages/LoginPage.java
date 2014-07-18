package com.everbridge.manager.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.everbridge.manager.common.AppDrivers;
import com.everbridge.manager.common.MobileScreen;

/**
 * Common class for mobile member login test, including login Page and login method
 * 
 * @ClassName LoginPage
 * @Description TODO
 * @author Misher
 * @date May 6, 2014
 */

public class LoginPage {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public LoginPage() {
		this.driver = AppDrivers.getDriver();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "net.everbridge:id/edit_login_user")
	public WebElement usernameInput;

	@FindBy(id = "net.everbridge:id/edit_login_pass")
	public WebElement passwordInput;

	@FindBy(id = "net.everbridge:id/user_del_btn")
	public WebElement usernameClear;

	@FindBy(id = "net.everbridge:id/pwd_del_btn")
	public WebElement passwordClear;

	@FindBy(id = "net.everbridge:id/btn_login")
	public WebElement loginBtn;

	@FindBy(id = "net.everbridge:id/remember_btn")
	public WebElement saveUsernameBtn;
	
	// The text is "Logging in..."
	@FindBy(id = "android:id/message")
	public WebElement loadingDialog;

	public LoginPage inputUserName(String userName) {
		usernameInput.clear();
		usernameInput.sendKeys(userName);
		return this;
	}

	public LoginPage inputPassword(String password) {
		passwordInput.clear();
		passwordInput.sendKeys(password);
		return this;
	}

	public void clickLoginBtn() {
		loginBtn.click();
	}
	
	public void login(String userName,String password) throws InterruptedException{
		usernameInput.clear();
		usernameInput.sendKeys(userName);
		passwordInput.clear();
		passwordInput.sendKeys(password);
		
		MobileScreen.putKeyCode(4, driver);
		Thread.sleep(5000);
		loginBtn.click();
		new WebDriverWait(driver, 60).until(
				(ExpectedConditions
						.invisibilityOfElementLocated(By.id("android:id/message"))));
	}

//	public void clickDoneBtn(){
//		doneBtn.click();
//	}
	
	public String getLoginMsg() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

}
