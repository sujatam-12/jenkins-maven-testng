package com.selenium.selenium_maven.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.selenium.selenium_maven.base.BaseTest;
import com.selenium.selenium_maven.utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        Object testClass = result.getInstance();

        if (testClass instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) testClass;

            String testName = result.getMethod().getMethodName();
            ScreenshotUtil.takeScreenshot(
                    baseTest.getDriver(),
                    testName + "_FAILED"
            );
        }
    }

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {}

    @Override
    public void onTestStart(ITestResult result) {}

    @Override
    public void onTestSuccess(ITestResult result) {}

    @Override
    public void onTestSkipped(ITestResult result) {}
}
