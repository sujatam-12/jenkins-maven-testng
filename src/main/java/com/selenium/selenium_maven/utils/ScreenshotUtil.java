
package com.selenium.selenium_maven.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    // ✅ FULL PAGE SCREENSHOT (already working)
    public static String takeScreenshot(WebDriver driver, String fileName) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String dir = System.getProperty("user.dir") + "/screenshots/";
        new File(dir).mkdirs();

        String path = dir + fileName + ".png";

        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    // ✅ ELEMENT SCREENSHOT (THIS FIXES YOUR ERROR)
    public static String takeElementScreenshot(
            WebDriver driver,
            WebElement element,
            String fileName) {

        File src = element.getScreenshotAs(OutputType.FILE);

        String dir = System.getProperty("user.dir") + "/screenshots/";
        new File(dir).mkdirs();

        String path = dir + fileName + ".png";

        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}





