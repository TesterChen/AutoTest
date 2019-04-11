package com.selenium.learn;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * TestNG annotations examples
 *
 */
public class TestNGTest {

	@BeforeClass
	public void beforeClass() {
		System.out.println("我是 @BeforeClass");
	}

	// @BeforeTest表示方法将在属于<test>标签内的类的所有测试方法运行之前运行
	@BeforeTest
	public void before() {
		System.out.println("我是 @BeforeTest");
	}

	// beforeMethod 在每次方法执行之前都会执行
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("我是 @BeforeMethod");
	}

	@Test(dependsOnMethods = { "test2" })
	public void test1() {
		System.out.println("我是 @Test1()");
	}

	// dependsOnMethods
	@Test(dependsOnMethods = { "test3" })
	public void test2() {
		System.out.println("我是 @Test2()");
	}

	// @Test表示这里是测试脚本主体
	@Test(dependsOnMethods = { "test4" })
	public void test3() {
		System.out.println("我是 @Test3()");
	}

	// @Test表示这里是测试脚本主体
	@Test
	public void test4() {
		System.out.println("我是 @Test4()");
	}

	// afterMethod 在每次方法执行之后都会执行
	@AfterMethod
	public void afterMethod() {
		System.out.println("我是 @AfterMethod");
	}

	// @AfterTest表示方法将在属于<test>标签内的类的所有测试方法运行之后运行
	@AfterTest
	public void after() {
		System.out.println("我是 @AfterTest");
		System.out.println();
	}

	@AfterClass
	public void afterClass() {
		System.out.println("我是 @AfterClass");
	}
}