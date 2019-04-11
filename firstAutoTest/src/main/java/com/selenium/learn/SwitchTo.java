package com.selenium.learn;

import java.util.Set;

import org.openqa.selenium.By;
/**
 * Hello world!
 *
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * simple selenium web test script
 *
 */
public class SwitchTo {
	WebDriver driver;

	@BeforeTest
	public void before() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		driver = new ChromeDriver();
	}

	@Test
	public void cookie() {
		driver.get("https://www.baidu.com/");
		String handle = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[@id='setf']")).click();
		sleep(2000);
		driver.switchTo().window(handle);
		sleep(2000);
		selectLastWindow();
		driver.close();
		sleep(2000);
		selectLastWindow();
		driver.findElement(By.xpath("//input[@id='kw']")).sendKeys("java");
		sleep(2000);

		// // 切换至默认页面或frame
		// driver.switchTo().defaultContent();
		// // 支持id或者name
		// driver.switchTo().frame("user");
		// // 通过元素定位的方式切换
		// WebElement element = driver.findElement(By.xpath("//frame[@id='user']"));
		// driver.switchTo().frame(element);
		// // 通过frame的索引切换
		// driver.switchTo().frame(1);
	}

	@AfterTest
	public void after() {
		driver.close();
		driver.quit();
	}

	// 切换至最后一个打开的页面
	public void selectLastWindow() {
		Set<String> handles = driver.getWindowHandles();
		int num = 0, aim = handles.size() - 1;
		for (String winHandle : handles) {
			if (aim == num) {
				driver.switchTo().window(winHandle);
			}
			num++;
		}
	}

	// 根据页面title切换页面
	public void selectWindowByTitle(String windowTitle) {
		for (String winHandle : driver.getWindowHandles()) {
			if (driver.switchTo().window(winHandle).getTitle().equals(windowTitle)) {
				break;
			}
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