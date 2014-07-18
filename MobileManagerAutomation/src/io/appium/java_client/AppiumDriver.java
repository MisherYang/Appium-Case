/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.appium.java_client.MobileCommand.*;

public class AppiumDriver extends RemoteWebDriver implements MobileDriver, ContextAware, Rotatable, FindsByIosUIAutomation,
        FindsByAndroidUIAutomator, FindsByAccessibilityId {

  private final static MobileErrorHandler errorHandler = new MobileErrorHandler();
  private URL remoteAddress;
  private ComplexFind complexFind;

  public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities){

    super(remoteAddress, desiredCapabilities);

    this.remoteAddress = remoteAddress;
    complexFind = new ComplexFind(this);

    ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap.builder();
    builder
            .put(RESET, postC("/session/:sessionId/appium/app/reset"))
            .put(GET_STRINGS, postC("/session/:sessionId/appium/app/strings"))
            .put(KEY_EVENT, postC("/session/:sessionId/appium/device/keyevent"))
            .put(CURRENT_ACTIVITY, getC("/session/:sessionId/appium/device/current_activity"))
            .put(SET_VALUE, postC("/session/:sessionId/appium/element/:id/value"))
            .put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"))
            .put(HIDE_KEYBOARD, postC("/session/:sessionId/appium/device/hide_keyboard"))
            .put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"))
            .put(RUN_APP_IN_BACKGROUND, postC("/session/:sessionId/appium/app/background"))
            .put(PERFORM_TOUCH_ACTION, postC("/session/:sessionId/touch/perform"))
            .put(PERFORM_MULTI_TOUCH, postC("/session/:sessionId/touch/multi/perform"))
            .put(IS_APP_INSTALLED, postC("/session/:sessionId/appium/device/app_installed"))
            .put(INSTALL_APP, postC("/session/:sessionId/appium/device/install_app"))
            .put(REMOVE_APP, postC("/session/:sessionId/appium/device/remove_app"))
            .put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"))
            .put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"))
            .put(END_TEST_COVERAGE, postC("/session/:sessionId/appium/app/end_test_coverage"))
            .put(LOCK, postC("/session/:sessionId/appium/device/lock"))
            .put(SHAKE, postC("/session/:sessionId/appium/device/shake"))
            .put(COMPLEX_FIND, postC("/session/:sessionId/appium/app/complex_find"))
            ;
    ImmutableMap<String, CommandInfo> mobileCommands = builder.build();

    HttpCommandExecutor mobileExecutor = new HttpCommandExecutor(mobileCommands, remoteAddress);
    super.setCommandExecutor(mobileExecutor);

  }

  @Override
  public Response execute(String driverCommand, Map<String, ?> parameters) {
    try {
      return super.execute(driverCommand, parameters);
    } catch (WebDriverException ex) {
      errorHandler.throwIfMobileError(ex);
    }

    throw new RuntimeException("An WebDriver error should have been thrown, if you're reading this, the problem is " +
            "definitely in the Appium Driver");
  }

  @Override
  protected Response execute(String command) {
    return execute(command, ImmutableMap.<String, Object>of());
  }


  /**
   * Reset the currently running app for this session
   */
  public void resetApp() {
    execute(MobileCommand.RESET);
  }

  /**
   * Get all defined Strings from an Android app for the default language
   *
   * @return a string of all the localized strings defined in the app
   */
  public String getAppStrings() {
    Response response = execute(GET_STRINGS);
    return response.getValue().toString();
  }

  /**
   * Get all defined Strings from an Android app for the specified language
   *
   * @param language strings language code
   * @return a string of all the localized strings defined in the app
   */
  public String getAppStrings(String language) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("language", language);
    ImmutableMap<String, Integer> parameters = builder.build();
    Response response = execute(GET_STRINGS, parameters);
    return response.getValue().toString();
  }

  /**
   * Send a key event to the device
   *
   * @param key code for the key pressed on the device
   */
  public void sendKeyEvent(int key) {
    sendKeyEvent(key, null);
  }

  /**
   * Send a key event along with an Android metastate to an Android device
   * Metastates are things like *shift* to get uppercase characters
   *
   * @param key code for the key pressed on the Android device
   * @param metastate metastate for the keypress
   */
  public void sendKeyEvent(int key, Integer metastate) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("keycode", key);
    if (metastate != null) { builder.put("metastate", metastate); }
    ImmutableMap<String, Integer> parameters = builder.build();
    execute(KEY_EVENT, parameters);
  }

  /**
   * Get the current activity being run on the mobile device
   */
  public String currentActivity() {
    Response response = execute(CURRENT_ACTIVITY);
    return response.getValue().toString();
  }

  /**
   *
   * @param remotePath On Android and iOS, this is either the path to the file (relative to the root of the app's file system).
   *                   On iOS only, if path starts with /AppName.app, which will be replaced with the application's .app directory
   * @return A byte array of Base64 encoded data.
   */
  public byte[] pullFile(String remotePath) {
    Response response = execute(PULL_FILE, ImmutableMap.of("path", remotePath));
    String base64String = response.getValue().toString();

    return DatatypeConverter.parseBase64Binary(base64String);
  }

  /**
   * Save base64 encoded data as a file on the remote mobile device.
   * This is an Android only method.
   * @param remotePath Path to file to write data to on remote device
   * @param base64Data Base64 encoded byte array of data to write to remote device
   */
  public void pushFile(String remotePath, byte[] base64Data) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("path", remotePath).put("data", base64Data);
    execute(PUSH_FILE, builder.build());
  }

  /**
   * Hides the keyboard if it is showing.
   * This is an iOS only command.
   */
  public void hideKeyboard() {
    execute(HIDE_KEYBOARD);
  }

  /**
   * Hides the keyboard by pressing the button specified by keyName if it is showing.
   * This is an iOS only command.
   * @param keyName The button pressed by the mobile driver to attempt hiding the keyboard
   */
  public void hideKeyboard(String keyName) {
    execute(HIDE_KEYBOARD, ImmutableMap.of("keyName", keyName));
  }

  /**
   * Runs the current app as a background app for the number of seconds requested.
   * This is a synchronous method, it returns after the back has been returned to the foreground.
   * @param seconds Number of seconds to run App in background
   */
  public void runAppInBackground(int seconds) {
    execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", seconds));
  }

  /**
   * Performs a chain of touch actions, which together can be considered an entire gesture.
   * See the Webriver 3 spec https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
   *
   * It's more convenient to call the perform() method of the TouchAction object itself.
   *
   * @param touchAction A TouchAction object, which contains a list of individual touch actions to perform
   * @return the same touchaction object
   */
  public TouchAction performTouchAction(TouchAction touchAction) {
    ImmutableMap<String, ImmutableList> parameters = touchAction.getParameters();
    touchAction.clearParameters();
    execute(PERFORM_TOUCH_ACTION, parameters);

    return touchAction;
  }

  /**
   * Performs multiple TouchAction gestures at the same time, to simulate multiple fingers/touch inputs.
   * See the Webriver 3 spec https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
   *
   * It's more convenient to call the perform() method of the MultiTouchAction object.
   *
   * @param multiAction the MultiTouchAction object to perform.
   */
  public void performMultiTouchAction(MultiTouchAction multiAction) {
    ImmutableMap<String, ImmutableList> parameters = multiAction.getParameters();
    execute(PERFORM_MULTI_TOUCH, parameters);
  }

  /**
   * Convenience method for tapping the center of an element on the screen
   * @param fingers number of fingers/appendages to tap with
   * @param element element to tap
   * @param duration how long between pressing down, and lifting fingers/appendages
   */
  public void tap(int fingers, WebElement element, int duration) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    for (int i = 0; i < fingers; i++) {
      multiTouch.add(createTap(element, duration));
    }

    multiTouch.perform();
  }

  /**
   * Convenience method for tapping a position on the screen
   * @param fingers number of fingers/appendages to tap with
   * @param x x coordinate
   * @param y y coordinate
   * @param duration
   */
  public void tap(int fingers, int x, int y, int duration) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    for (int i = 0; i < fingers; i++) {
      multiTouch.add(createTap(x, y, duration));
    }

    multiTouch.perform();
  }

  /**
   * Convenience method for swiping across the screen
   * @param startx starting x coordinate
   * @param starty starting y coordinate
   * @param endx ending x coordinate
   * @param endy ending y coordinate
   * @param duration amount of time in milliseconds for the entire swipe action to take
   */
  public void swipe(int startx, int starty, int endx, int endy, int duration) {
    TouchAction touchAction = new TouchAction(this);

    //appium converts press-wait-moveto-release to a swipe action
    touchAction.press(startx, starty).waitAction(duration).moveTo(endx, endy).release();

    touchAction.perform();
  }

  /**
   * Convenience method for pinching an element on the screen.
   * "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
   * NOTE:
   * This convenience method places the initial touches around the element, if this would happen to place one of them
   * off the screen, appium with return an outOfBounds error. In this case, revert to using the MultiTouchAction api
   * instead of this method.
   *
   * @param el The element to pinch
   */
  public void pinch(WebElement el) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    Dimension dimensions = el.getSize();
    Point upperLeft = el.getLocation();
    Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);

    TouchAction action0 = new TouchAction(this).press(el, center.getX(), center.getY() - 100).moveTo(el).release();
    TouchAction action1 = new TouchAction(this).press(el, center.getX(), center.getY() + 100).moveTo(el).release();

    multiTouch.add(action0).add(action1);

    multiTouch.perform();
  }

  /**
   * Convenience method for pinching an element on the screen.
   * "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
   * NOTE:
   * This convenience method places the initial touches around the element at a distance, if this would happen to place
   * one of them off the screen, appium will return an outOfBounds error. In this case, revert to using the
   * MultiTouchAction api instead of this method.
   *
   * @param x x coordinate to terminate the pinch on
   * @param y y coordinate to terminate the pinch on
   */
  public void pinch(int x, int y) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    TouchAction action0 = new TouchAction(this).press(x, y-100).moveTo(x, y).release();
    TouchAction action1 = new TouchAction(this).press(x, y+100).moveTo(x, y).release();

    multiTouch.add(action0).add(action1);

    multiTouch.perform();
  }

  /**
   * Convenience method for "zooming in" on an element on the screen.
   * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
   * NOTE:
   * This convenience method slides touches away from the element, if this would happen to place one of them
   * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
   * instead of this method.
   *
   * @param el The element to pinch
   */
  public void zoom(WebElement el) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    Dimension dimensions = el.getSize();
    Point upperLeft = el.getLocation();
    Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);

    TouchAction action0 = new TouchAction(this).press(el).moveTo(el, center.getX(), center.getY() - 100).release();
    TouchAction action1 = new TouchAction(this).press(el).moveTo(el, center.getX(), center.getY() + 100).release();

    multiTouch.add(action0).add(action1);

    multiTouch.perform();
  }

  /**
   * Convenience method for "zooming in" on an element on the screen.
   * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
   * NOTE:
   * This convenience method slides touches away from the element, if this would happen to place one of them
   * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
   * instead of this method.
   *
   * @param x x coordinate to start zoom on
   * @param y y coordinate to start zoom on
   */
  public void zoom(int x, int y) {
    MultiTouchAction multiTouch = new MultiTouchAction(this);

    TouchAction action0 = new TouchAction(this).press(x, y).moveTo(x, y-100).release();
    TouchAction action1 = new TouchAction(this).press(x, y).moveTo(x, y+100).release();

    multiTouch.add(action0).add(action1);

    multiTouch.perform();
  }

   /**
   * In iOS apps, named TextFields have the same accessibility Id as their containing TableElement.
   * This is a convenience method for getting the named TextField, rather than its containing element.
   * @param name accessiblity id of TextField
   * @return The textfield with the given accessibility id
   */
  public WebElement getNamedTextField(String name) {
    RemoteWebElement element = (RemoteWebElement) findElementByAccessibilityId(name);
    if (element.getTagName() != "TextField") {
      MobileElement mobileElement = new MobileElement(element, this);
      return mobileElement.findElementByAccessibilityId(name);
    }

    return element;
  }

  /**
   * Checks if an app is installed on the device
   * @param bundleId bundleId of the app
   * @return True if app is installed, false otherwise
   */
  public boolean isAppInstalled(String bundleId) {
    Response response = execute(IS_APP_INSTALLED, ImmutableMap.of("bundleId", bundleId));

    return Boolean.parseBoolean(response.getValue().toString());
  }

  /**
   * Install an app on the mobile device
   * @param appPath path to app to install
   */
  public void installApp(String appPath) {
    execute(INSTALL_APP, ImmutableMap.of("appPath", appPath));
  }

  /**
   * Remove the specified app from the device (uninstall)
   * @param bundleId the bunble identifier (or app id) of the app to remove
   */
  public void removeApp(String bundleId) {
    execute(REMOVE_APP, ImmutableMap.of("bundleId", bundleId));
  }

  /**
   * Launch the app which was provided in the capabilities at session creation
   */
  public void launchApp() {
    execute(LAUNCH_APP);
  }

  /**
   * Close the app which was provided in the capabilities at session creation
   */
  public void closeApp() {
    execute(CLOSE_APP);
  }

  /**
   * Get test-coverage data
   * Android-only method
   * @param intent intent to broadcast
   * @param path path to .ec file
   */
  public void endTestCoverage(String intent, String path) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("intent", intent).put("path", path);
    execute(END_TEST_COVERAGE, builder.build());
  }

  /**
   * Lock the device (bring it to the lock screen) for a given number of seconds
   * @param seconds number of seconds to lock the screen for
   */
  public void lockScreen(int seconds) {
    execute(LOCK, ImmutableMap.of("seconds", seconds));
  }

  /**
   * Simulate shaking the device
   * This is an iOS-only method
   */
  public void shake() {
    execute(SHAKE);
  }

  public MobileElement complexFind(String complex) {
    return complexFind.execute(complex);
  }

  public MobileElement scrollTo(String text) {
    return complexFind.scrollTo(text);
  }

  public MobileElement scrollToExact(String text) {
    return complexFind.scrollToExact(text);
  }

  @Override
  public WebDriver context(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Must supply a context name");
    }

    execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
    return AppiumDriver.this;
  }

  @Override
  public Set<String> getContextHandles() {
    Response response = execute(DriverCommand.GET_CONTEXT_HANDLES);
    Object value = response.getValue();
    try {
      List<String> returnedValues = (List<String>)value;
      return new LinkedHashSet<String>(returnedValues);
    } catch (ClassCastException ex) {
      throw new WebDriverException("Returned value cannot be converted to List<String>: " + value, ex);
    }
  }

  @Override
  public String getContext() {
    String contextName = String.valueOf(execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
    if (contextName.equals("null")) {
      return null;
    }
    return contextName;
  }

  @Override
  public void rotate(ScreenOrientation orientation) {
    execute(DriverCommand.SET_SCREEN_ORIENTATION, ImmutableMap.of("orientation", orientation.value().toUpperCase()));
  }

  @Override
  public ScreenOrientation getOrientation() {
    Response response = execute(DriverCommand.GET_SCREEN_ORIENTATION);
    String orientation = response.getValue().toString().toLowerCase();
    if (orientation.equals(ScreenOrientation.LANDSCAPE.value())) {
      return ScreenOrientation.LANDSCAPE;
    } else if (orientation.equals(ScreenOrientation.PORTRAIT.value())) {
      return ScreenOrientation.PORTRAIT;
    } else {
      throw new WebDriverException("Unexpected orientation returned: " + orientation);
    }
  }

  @Override
  public WebElement findElementByIosUIAutomation(String using) {
    return findElement("-ios uiautomation", using);
  }

  @Override
  public List<WebElement> findElementsByIosUIAutomation(String using) {
    return findElements("-ios uiautomation", using);
  }

  @Override
  public WebElement findElementByAndroidUIAutomator(String using) {
    return findElement("-android uiautomator", using);
  }

  @Override
  public List<WebElement> findElementsByAndroidUIAutomator(String using) {
    return findElements("-android uiautomator", using);
  }

  @Override
  public WebElement findElementByAccessibilityId(String using) {
    return findElement("accessibility id", using);
  }

  @Override
  public List<WebElement> findElementsByAccessibilityId(String using) {
    return findElements("accessibility id", using);
  }

  private TouchAction createTap(WebElement element, int duration) {
    TouchAction tap = new TouchAction(this);
    return tap.press(element).waitAction(duration).release();
  }

  private TouchAction createTap(int x, int y, int duration) {
    TouchAction tap = new TouchAction(this);
    return tap.press(x, y).waitAction(duration).release();
  }

  private static CommandInfo getC(String url) {
    return new CommandInfo(url, HttpVerb.GET);
  }

  private static CommandInfo postC(String url) {
    return new CommandInfo(url, HttpVerb.POST);
  }

  private static CommandInfo deleteC(String url) {
    return new CommandInfo(url, HttpVerb.DELETE);
  }

  public URL getRemoteAddress() {
      return remoteAddress;
  }
}