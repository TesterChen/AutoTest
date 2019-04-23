package com.testerchen.qa.multiple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 通过@Test注解实现单测试方法迸发
 * @Test(threadPoolSize = 2, invocationCount = 2)
 * threadPoolSize 线程池大小，invocationCount 方法需要运行的次数
 *
 */
public class threadMultipleTest {

	public static ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();
	String projectPath = System.getProperty("user.dir");

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		ThreadDriver.set(driver);
	}

	@Test(threadPoolSize = 2, invocationCount = 2)
	public void first() {
		ThreadDriver.get().get("https://www.baidu.com");
		ThreadDriver.get().findElement(By.id("kw")).sendKeys("test");
		ThreadDriver.get().findElement(By.id("su")).click();
		sleep(3000);
	}

	@Test(threadPoolSize = 2, invocationCount = 1)
	public void second() {
		ThreadDriver.get().get("https://www.baidu.com");
		ThreadDriver.get().findElement(By.id("kw")).sendKeys("selenium");
		ThreadDriver.get().findElement(By.id("su")).click();
		sleep(3000);
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
