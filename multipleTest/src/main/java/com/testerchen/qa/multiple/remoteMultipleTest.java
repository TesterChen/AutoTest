package com.testerchen.qa.multiple;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * 通过Grid远程浏览器并发执行
 * 需要借助TestNG.xml的配置实现(RemoteTestNG.xml)
 *
 */

public class remoteMultipleTest {

	public static ThreadLocal<RemoteWebDriver> ThreadRemoteDriver = new ThreadLocal<RemoteWebDriver>();

	@Parameters({ "seleniumHubHost", "seleniumHubPort", "browserType" })
	@BeforeMethod
	public void beforeMetho(String seleniumHubHost, String seleniumHubPort, String browserType) throws MalformedURLException {
		RemoteWebDriver driver;
		DesiredCapabilities capabilities;
		
		switch (browserType) {
		case "firefox":
			capabilities = DesiredCapabilities.firefox();
			break;
		case "chrome":
			capabilities = DesiredCapabilities.chrome();
			break;
		default:
			capabilities = DesiredCapabilities.chrome();
			break;
		}
		driver = new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":" + seleniumHubPort + "/wd/hub"),
				capabilities);
		ThreadRemoteDriver.set(driver);
	}

	@Test
	public void test1() {
		ThreadRemoteDriver.get().get("https://www.baidu.com");
		ThreadRemoteDriver.get().findElement(By.id("kw")).sendKeys("test");
		ThreadRemoteDriver.get().findElement(By.id("su")).click();
		sleep(3000);
	}

	@Test
	public void test2() {
		ThreadRemoteDriver.get().get("https://www.baidu.com");
		ThreadRemoteDriver.get().findElement(By.id("kw")).sendKeys("selenium");
		ThreadRemoteDriver.get().findElement(By.id("su")).click();
		sleep(3000);
	}

	@AfterMethod
	public void afterMethod() {
		ThreadRemoteDriver.get().quit();
		ThreadRemoteDriver.remove();
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
