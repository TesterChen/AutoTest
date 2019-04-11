package com.selenium.reports.script;

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

import junit.framework.Assert;

/**
 * simple selenium web test script
 *
 */
public class ReprotTest {
	WebDriver driver;

	// @BeforeTest注解表示before()这个方法在测试开始之前执行
	@BeforeTest
	public void before() {
		// 获取当前项目路径
		String projectPath = System.getProperty("user.dir");
		// 设置chromedriver服务地址，这里用到了前面存放driver的地址
		System.setProperty("webdriver.chrome.driver", projectPath+"/src/main/resource/driver/chromedriver");
		// 实例化一个chrome driver，也就是一个浏览器d
		driver = new ChromeDriver();
	}

	// @Test注解表示这里是测试脚本主体
	@Test(testName="百度搜索java")
	public void searchJava() {
		// 打开目标网站
		driver.get("http://www.baidu.com/");
		// 在目标输入框中输入selenium
		driver.findElement(By.xpath("//input[@id='kw']")).sendKeys("java");
		// 点击搜索按钮
		driver.findElement(By.xpath("//input[@id='su']")).click();
		// 等待3s
		sleep(3000);
	}
	
	@Test
	public void testFail() {
		Assert.assertEquals("true", "false");
	}

	// @AfterTest注解中往往放一些资源释放类动作
	@AfterTest
	public void after() {
		// 关闭和退出浏览器，释放资源
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