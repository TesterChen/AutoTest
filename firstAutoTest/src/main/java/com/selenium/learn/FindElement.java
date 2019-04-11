package com.selenium.learn;

/**
 * Hello world!
 *
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * simple selenium web test script
 *
 */
public class FindElement {
	WebDriver driver;

	@BeforeTest
	public void before() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resource/driver/chromedriver");
		driver = new ChromeDriver();
	}

	@Test
	public void test() {
		driver.get("http://www.baidu.com/");
		driver.findElement(By.name("wd")).sendKeys("Java");
		driver.findElement(By.name("wd")).clear();
		
		driver.findElement(By.cssSelector("input.s_ipt")).sendKeys("Java");
		driver.findElement(By.cssSelector("input.s_ipt")).clear();
		
		driver.findElement(By.xpath("//input[@id='kw']")).sendKeys("selenium");
		driver.findElement(By.xpath("//input[@id='kw']")).clear();
		
		driver.findElements(By.id("kw")).get(0).sendKeys("Java");
		driver.findElements(By.id("kw")).get(0).clear();
		
		driver.findElement(By.xpath("//input[@id='su']")).click();
		
		driver.findElements(By.xpath(".//a[contains(@class,'mnav')]")).get(0).click();
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