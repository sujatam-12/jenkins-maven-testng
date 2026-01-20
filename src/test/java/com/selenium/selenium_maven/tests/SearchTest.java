package com.selenium.selenium_maven.tests;

import com.selenium.selenium_maven.actions.SearchActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchTest {

    private WebDriver driver;
    private SearchActions searchActions;

    @BeforeMethod
    public void setUp() {

        // 🔹 CHANGE BROWSER HERE
        String browser = "chrome"; // chrome | firefox

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } 
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } 
        else {
            throw new RuntimeException("Invalid browser name");
        }

        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/index.php");

        // ✅ IMPORTANT: initialize action class
        searchActions = new SearchActions(driver);
    }

    @Test
    public void validateSearchFunctionality() {

        searchActions.searchForProduct("Dress");

        Assert.assertTrue(
                searchActions.validateSearchResults(),
                "Search results are NOT displayed"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
