package com.testerchen.qa.multiple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 借助TestNG.xml的配置实现本地浏览器并发执行(TestNG.xml)
 *
 */
public class multipleTest {

	public static ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();
	String projectPath = System.getProperty("user.dir");

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		ThreadDriver.set(driver);
	}

	@Test()
	public void first() {
		ThreadDriver.get().get("https://www.baidu.com");
		ThreadDriver.get().findElement(By.id("kw")).sendKeys("test");
		ThreadDriver.get().findElement(By.id("su")).click();
		sleep(5000);
	}

	@Test()
	public void second() {
		ThreadDriver.get().get("https://www.baidu.com");
		ThreadDriver.get().findElement(By.id("kw")).sendKeys("selenium");
		ThreadDriver.get().findElement(By.id("su")).click();
		sleep(5000);
	}

	@AfterMethod
	public void afterMethod() {
		ThreadDriver.get().quit();
		ThreadDriver.remove();
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
