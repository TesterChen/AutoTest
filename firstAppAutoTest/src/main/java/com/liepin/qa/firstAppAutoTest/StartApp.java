package com.liepin.qa.firstAppAutoTest;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class StartApp {
	AppiumDriver<MobileElement> driver;

	@BeforeTest
	public void start() {
		startWayOne();
		// startWayTwo();
	}

	@Test
	public void getAppDriver() throws MalformedURLException {
		// 点击第一个职位
		driver.findElement(By.xpath("(//XCUIElementTypeStaticText[contains(@name,'测试')])[1]")).click();
		sleep(5000);
	}

	@AfterTest
	public void quite() {
		driver.quit();
	}

	public void startWayOne() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "12.2");
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability("deviceName", "iPhone");
		// liepin app，还可以使用"app	":本地.ipa文件路径
		capabilities.setCapability("bundleId", "com.lietou.insw-c-ios-iphone");
		// 只有一台设备时可以填写auto
		capabilities.setCapability("udid", "auto");
		// 解决App启动慢或失败问题，appium 1.12.0新增
		capabilities.setCapability("eventLoopIdleDelaySec", 10);
		// 自定义超时时间
		capabilities.setCapability("newCommandTimeout", 120);
		try {
			driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Please start the appium service first");
		}
	}
	
	public void startWayTwo() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
		// 只有一台设备时可以填写auto
		capabilities.setCapability(MobileCapabilityType.UDID, "auto");
		// 自定义超时时间
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		// ios应用的唯一标识，还可以使用"app	":本地.ipa文件路径
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
