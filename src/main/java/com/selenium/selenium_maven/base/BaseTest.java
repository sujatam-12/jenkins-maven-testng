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
    public void setUp(@Optional("chrome") String browser) {
    	// 🔑 INIT EXTENT HERE (FIX)
        if (extent == null) {
            extent = ExtentManager.getExtentReport();
        }
        launchBrowser(browser);
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
        //driver.get("http://automationpractice.pl/index.php");
        driver.get("https://automationexercise.com");
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

/*package com.selenium.selenium_maven.base;
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

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {

        if (extent == null) {
            extent = ExtentManager.getExtentReport();
        }

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://automationexercise.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}*/

