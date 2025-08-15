package com.qa.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Page;
import com.qa.factory.PlaywrightFactory;




public class ExtentReportListener implements ITestListener {
	
	private static final String OUTPUT_FOLDER="./build/";
	//private static final String FILE_NAME ="TestExecutionReport.html";
	
	private static ExtentReports extent=init();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
  


	
	private static ExtentReports init() {
		
		Path  path = Paths.get(OUTPUT_FOLDER);
		
		if(!Files.exists(path)) {
			try {
			Files.createDirectories(path);
			}catch(IOException e){
			e.printStackTrace();
		}
		}
		
		extentReports= new ExtentReports();
		
		 String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		    String FILE_NAME = "TestExecutionReport_" + timestamp + ".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER+FILE_NAME);
		reporter.config().setReportName("QA Test Automation Report Results");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("System", "Windows");
		extentReports.setSystemInfo("Author", "Om Shanker Goyal");
		
		return extentReports;
	
	}
	
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite Started!");
	}
	
	public synchronized void onFinish(ITestContext context) {
		System.out.println("Test suite is ending");
		extent.flush();
		test.remove();
	}
	
	public synchronized void onTestStart(ITestResult result) {
		String methodname=result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf('.');
		int mid =qualifiedName.substring(0,last).lastIndexOf('.');
		String className =qualifiedName.substring(mid+1, last);
		
		System.out.println(methodname + "started");
		
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
		
	
		
	}
	
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + "passed!");
		test.get().pass("Test passed!");
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + "failed!");
		try {
			
			Page page = (Page) result.getTestContext().getAttribute("page");
			 String screenshotPath = PlaywrightFactory.takeFullPageScreenshot(page);
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + "skipped!");
		try {
			Page page = (Page) result.getTestContext().getAttribute("page");
			 String screenshotPath = PlaywrightFactory.takeFullPageScreenshot(page);
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		
	}
	
	public synchronized void onTestFailedButWithSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithSuccessPercentage for"+ result.getMethod().getMethodName());
	}
	
	

	private Date getTime(long millis) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	
}