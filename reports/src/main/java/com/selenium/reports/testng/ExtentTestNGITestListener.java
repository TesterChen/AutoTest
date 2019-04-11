package com.selenium.reports.testng;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentTestNGITestListener implements ITestListener {

	// 输出的目录和文件名可以修改
	private static ExtentReports extent = ExtentManager.createInstance("test-output/extent.html");
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	@Override
	public synchronized void onStart(ITestContext context) {
		ExtentTest parent = extent.createTest(getClass().getName());
		parentTest.set(parent);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		ExtentTest child = ((ExtentTest) parentTest.get()).createNode(result.getMethod().getMethodName());
		test.set(child);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		((ExtentTest) test.get()).pass("Test passed");
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		((ExtentTest) test.get()).fail(result.getThrowable());
		// 错误时截图
		try {
			// 截图名称固定，避免结果文件夹图片过多
			captureScreen(System.getProperty("user.dir") + "/test-output/", "screenshot.png");
			test.get().fail("fail details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
		} catch (Exception e) {	}
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		((ExtentTest) test.get()).skip(result.getThrowable());
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	// 截图方法
	public void captureScreen(String fileName, String folder) throws Exception {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		// 截图保存的路径
		File screenFile = new File(fileName);
		// 如果路径不存在,则创建
		if (!screenFile.getParentFile().exists()) {
			screenFile.getParentFile().mkdirs();
		}
		// 判断文件是否存在，不存在就创建文件
		if (!screenFile.exists() && !screenFile.isDirectory()) {
			screenFile.mkdir();
		}

		File file = new File(screenFile, folder);
		ImageIO.write(image, "png", file);
	}
}

class ExtentManager {

	private static ExtentReports extent;
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance("test-output/extent.html");
		return extent;
	}

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// 作者相关信息
		extent.createTest("Tester Chen").assignAuthor("QA TEAM").pass("details");

		return extent;
	}
}
