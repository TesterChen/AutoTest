package com.selenium.learn;

import java.util.Set;

/**
 * Hello world!
 *
 */
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * simple selenium web test script
 *
 */
public class CookieTest {
	WebDriver driver;

	@BeforeTest
	public void before() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/src/main/resource/driver/chromedriver");
		driver = new ChromeDriver();
	}

	@Test
	public void cookie() {
		driver.get("https://www.baidu.com/");
		// 增加cookie
		Cookie cookie = new Cookie("mykey", "myvalue");
		driver.manage().addCookie(cookie);
		// 获取并打印cookie
		System.out.println("mykey的值：" + driver.manage().getCookieNamed("mykey").getValue());
		System.out.println("mykey所在域名：" + driver.manage().getCookieNamed("mykey").getDomain());
		
		// 暂停检查是cookie是否添加成功
		sleep(30000);
		// 删除指定cookie
		driver.manage().deleteCookieNamed("mykey");
		
		// 如果key不存在那么会返回一个null
		Cookie nullCookie = driver.manage().getCookieNamed("mykey");
		System.out.println("删除mykey后，再获取它的值：" + nullCookie);
		// 若此时再去获取Cookie对象的其他属性，会抛出空指针异常
		// System.out.println("mykey的值：" + nullCookie.getValue());
		
		// 遍历当前域名下cookie
		System.out.println("================");
		Set<Cookie> cookies2 = driver.manage().getCookies();
		for (Cookie ck : cookies2) {
			System.out.println("cookie == "+ ck.getName() + ":" + ck.getValue());
		}
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