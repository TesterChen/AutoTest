package com.liepin.qa.firstAppAutoTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class CommonMethods {
	AppiumDriver<MobileElement> driver;

	@BeforeTest
	public void init() {
		start();
	}

	@Test
	public void getAppDriver() throws MalformedURLException {
		// 上滑
		up();
		// 下拉
		down();
		
		// 第一个职位
		WebElement webElement = driver.findElement(By.xpath("(//XCUIElementTypeStaticText[contains(@name,'测试')])[1]"));
		System.out.println("find the element");
	
		// 在元素上左滑
		swipeElement(webElement);
		// 屏幕向右滑动
		swipeScreenToRight();
		// 长按，如果有点击事件，会产生点击事件
		longPress(webElement, 3000);
		// 单击
		// webElement.click();
		// 返回
		driver.navigate().back();
		sleep(3000);
	}

	@AfterTest
	public void quite() {
		driver.quit();
	}

	/***
	 * 向右滑动屏幕
	 */
	public void swipeScreenToRight() {
		int x = driver.manage().window().getSize().getWidth();
		int y = driver.manage().window().getSize().getHeight();
		new TouchAction(driver).longPress(PointOption.point((int)(x * 0.1), y / 2))
				.moveTo(ElementOption.point((int)(x * 0.9), y / 2))
				.release().perform();
	}
	
	/***
	 * 向左滑动屏幕
	 */
	public void swipeScreenToLeft() {
		int x = driver.manage().window().getSize().getWidth();
		int y = driver.manage().window().getSize().getHeight();
		new TouchAction(driver).longPress(PointOption.point((int)(x * 0.9), y / 2))
				.moveTo(ElementOption.point((int)(x * 0.1), y / 2))
				.release().perform();
	}

	/***
	 * 元素左滑
	 * 
	 * @param webElement
	 */
	public void swipeElement(WebElement webElement) {
		// 不能使用press方法，会产生点击事件
		new TouchAction(driver).longPress(ElementOption.element(webElement))
				.moveTo(ElementOption.point(webElement.getLocation().getX() / 5, webElement.getLocation().getY()))
				.release().perform();
	}

	/**
	 * 上滑
	 */
	public void up() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: swipe", scrollObject);
	}

	/***
	 * 下拉
	 */
	public void down() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: swipe", scrollObject);
	}

	/***
	 * 长按元素
	 * 
	 * @param webElement
	 * @param timeout
	 */
	public void longPress(WebElement webElement, int timeout) {
		new TouchAction(driver).longPress(ElementOption.element(webElement))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeout))).release().perform();
	}

	/***
	 * 启动app
	 */
	public void start() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
		// 只有一台设备时可以填写auto
		capabilities.setCapability(MobileCapabilityType.UDID, "auto");
		// 自定义超时时间
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		// ios应用的唯一标识，还可以使用"app ":本地.ipa文件路径
		capabilities.setCapability("bundleId", "com.lietou.insw-c-ios-iphone");
		// 解决App启动慢或失败问题，appium 1.12.0新增
		capabilities.setCapability("eventLoopIdleDelaySec", 10);
		try {
			driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Please start the appium service first");
		}
	}

	// 等待方法是为了让我们更好的看到自动化执行结果
	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
