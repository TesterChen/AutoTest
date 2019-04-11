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
public class Functions {
	WebDriver driver;

	@BeforeTest
	public void before() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		driver = new ChromeDriver();
	}


	@Test
	public void test() {
		driver.manage().window().maximize();
		driver.manage().window().fullscreen();
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
		// driver.quit();
	}
	
	@Test
	public void test1() {
		// 打开页面
		driver.get("http://www.baidu.com/");
		// 获取当前页面url
		String url = driver.getCurrentUrl();
		System.out.println("url:" + url);
		// 获取当前页面title
		String title = driver.getTitle();
		System.out.println("title:" + title);
		// 获取页面源代码
		String source = driver.getPageSource();
		System.out.println("source top 255:" + source.substring(0, 255));
		// 获取当前页面的句柄
		String handle = driver.getWindowHandle();
		System.out.println("handle:" + handle.toString());
		// 获取当前浏览器的所有页面句柄
		Set<String> sets = driver.getWindowHandles();
		for(String str : sets) {
			System.out.println("windowHandle:" + str);
		}
	}
	
	@Test
	public void test2() {
		driver.get("http://www.baidu.com/");
		// 输入文本
		driver.findElement(By.xpath("//input[@id='kw']")).sendKeys("selenium");
		// 点击
		driver.findElements(By.xpath("//input[@id='su']")).get(0).click();
	}
	
	@Test
	public void test3() {
		driver.get("http://www.baidu.com/");
		// 获取元素
		WebElement element = driver.findElement(By.xpath("//input[@id='kw']"));
		// 输入文本
		element.sendKeys("selenium");
		// 点击
		element.click();
	}

	@AfterTest
	public void after() {
		driver.close();
		driver.quit();
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