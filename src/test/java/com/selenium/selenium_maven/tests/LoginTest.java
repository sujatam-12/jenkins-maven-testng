/*package com.selenium.selenium_maven.tests;

import com.selenium.selenium_maven.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/index.php");

        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginTestData() {
        return new Object[][]{
                // Valid credentials (replace with real registered email)
                {"validemail@test.com", "validpassword", true},

                // Invalid credentials
                {"invalid@test.com", "wrongpass", false},
                {"", "password", false},
                {"test@test.com", "", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void validateLogin(String email, String password, boolean isValidUser) {

        loginPage.login(email, password);

        if (isValidUser) {
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Login should be successful with valid credentials"
            );
        } else {
            Assert.assertTrue(
                    loginPage.isLoginFailed(),
                    "Login should fail with invalid credentials"
            );
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
*/


/*package com.selenium.selenium_maven.tests;

import com.selenium.selenium_maven.base.BaseTest;
import com.selenium.selenium_maven.pages.LoginPage;
import com.selenium.selenium_maven.utils.DataGenerator;
import com.selenium.selenium_maven.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseTest {

    LoginPage loginPage;
    String validEmail;
    String validPassword;

    @BeforeClass
    @Parameters("browser")
    public void createValidAccount(@Optional("firefox") String browser) {
        launchBrowser(browser);
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        validEmail = DataGenerator.generateEmail();
        validPassword = DataGenerator.generatePassword();

        loginPage.createAccount(validEmail, validPassword);
        Assert.assertTrue(loginPage.isLoginSuccessful());

        driver.quit();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser) {
        launchBrowser(browser);
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {

        JsonNode data = JsonUtils.readJson("testdata.json");
        JsonNode invalidUsers = data.get("invalidCredentials");

        Object[][] testData = new Object[invalidUsers.size() + 1][3];

        // Valid login
        testData[0] = new Object[]{validEmail, validPassword, true};

        for (int i = 0; i < invalidUsers.size(); i++) {
            testData[i + 1] = new Object[]{
                    invalidUsers.get(i).get("email").asText(),
                    invalidUsers.get(i).get("password").asText(),
                    false
            };
        }
        return testData;
    }

    @Test(dataProvider = "loginData")
    public void validateLogin(String email, String password, boolean expected) {

        loginPage.login(email, password);

        if (expected) {
            Assert.assertTrue(loginPage.isLoginSuccessful());
        } else {
            Assert.assertTrue(loginPage.isLoginFailed());
        }
    }
}*/

package com.selenium.selenium_maven.tests;

import com.selenium.selenium_maven.base.BaseTest;
import com.selenium.selenium_maven.pages.LoginPage;
import com.selenium.selenium_maven.utils.DataGenerator;
import com.selenium.selenium_maven.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseTest {

    LoginPage loginPage;

    static String validEmail;
    static String validPassword;
    static boolean accountCreated = false;

    // 🔹 Generate valid credentials ONCE (before DataProvider runs)
    @BeforeClass
    public void generateValidCredentials() {
        validEmail = DataGenerator.generateEmail();
        validPassword = DataGenerator.generatePassword();
    }

    // 🔹 Runs before EVERY test method
    @BeforeMethod
    public void initTest() {

        loginPage = new LoginPage(driver);

        // Always open login page
        loginPage.openLoginPage();

        // Create account only ONCE
        if (!accountCreated) {
            loginPage.createAccount(validEmail, validPassword);
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Account creation failed"
            );
            accountCreated = true;
        }
    }

    // 🔹 DataProvider (UNCHANGED pattern)
    @DataProvider(name = "loginData")
    public Object[][] loginData() {

        JsonNode data = JsonUtils.readJson("testdata.json");
        JsonNode invalidUsers = data.get("invalidCredentials");

        Object[][] testData = new Object[invalidUsers.size() + 1][3];

        // Valid login
        testData[0] = new Object[]{validEmail, validPassword, true};

        // Invalid logins
        for (int i = 0; i < invalidUsers.size(); i++) {
            testData[i + 1] = new Object[]{
                    invalidUsers.get(i).get("email").asText(),
                    invalidUsers.get(i).get("password").asText(),
                    false
            };
        }
        return testData;
    }

    // 🔹 Test method
    @Test(dataProvider = "loginData")
    public void validateLogin(String email, String password, boolean expected) {

        if (expected) {
            // User is already logged in from setup
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "User should already be logged in"
            );
        } else {
            // Invalid login scenario
            loginPage.openLoginPage();
            loginPage.login(email, password);

            Assert.assertTrue(
                    loginPage.isLoginFailed(),
                    "Login should fail for invalid credentials"
            );
        }
    }}