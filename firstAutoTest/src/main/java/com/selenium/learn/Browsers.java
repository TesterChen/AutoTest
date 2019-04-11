package com.selenium.learn;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

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
public class Browsers {
	WebDriver driver;

	@BeforeTest
	public void before() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		driver = new ChromeDriver();
	}

	@Test
	public void driver1() {
		driver.get("https://www.baidu.com/");
		WebElement element1 = driver.findElement(By.xpath("//input[@id='kw']"));
		System.out.println("刷新前页面元素hashcode:" + element1.hashCode());
		System.out.println();
		driver.navigate().refresh();
		System.out.println("刷新后直接打印原来元素hashcode:" + element1.hashCode());
		System.out.println();
		WebElement element2 = driver.findElement(By.xpath("//input[@id='kw']"));
		// 刷新之后重新获取元素，元素的hashcode会变化
		System.out.println("刷新后再次获取元素并打印hashcode:" + element2.hashCode());
	}

	@AfterTest
	public void after() {
		driver.close();
		driver.quit();
	}
	
	public void deletes(String xpath) {
		List<WebElement> elementList;
			int listSize = driver.findElements(By.xpath(xpath)).size();
			for (int index = 0; index < listSize; index++) {
				elementList = driver.findElements(By.xpath(xpath));
				elementList.get(0).click();
				driver.navigate().refresh();
			}
		}
	
	// 使用TakesScreenshot截取浏览器部分图，这里需要传入存储的位置和图片名称
	public void getScreenShot(String path) {
		try {
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File(path));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// 切换至最后一个打开的页面
	public void selectLastWindow() {
		Set<String> handles = driver.getWindowHandles();
		int num = 0, aim = handles.size() -1;
		for (String winHandle : handles) {
			if (aim == num) {
				driver.switchTo().window(winHandle);
			}
			num++;
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