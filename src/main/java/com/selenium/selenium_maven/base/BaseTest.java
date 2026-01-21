/*package com.selenium.selenium_maven.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class BaseTest {

    protected WebDriver driver;

    protected void launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/index.php");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}*/

/*package com.selenium.selenium_maven.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        launchBrowser("chrome"); // ✅ DRIVER IS NOW INITIALIZED
    }

    protected void launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/index.php");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}*/

package com.selenium.selenium_maven.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.selenium.selenium_maven.utils.ExtentManager;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    
    /* ---------- BROWSER SETUP ---------- */
    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) {
    	// 🔑 INIT EXTENT HERE (FIX)
        if (extent == null) {
            extent = ExtentManager.getExtentReport();
        }
        launchBrowser(browser);
    }

    protected void launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.chromedriver().setup();
            driver = new FirefoxDriver();
        }
        
        else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/index.php");
    }
    /* ---------- CLEANUP ---------- */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /* ---------- EXTENT REPORT FLUSH ---------- */
    @AfterClass(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}


