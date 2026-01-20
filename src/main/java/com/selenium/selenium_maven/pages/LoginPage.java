/*package com.selenium.selenium_maven.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    
    
    // Sign in link
    @FindBy(className = "login")
    private WebElement signInLink;
    
  // Create account
    @FindBy(id = "email_create")
    private WebElement emailCreate;

    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;
    
    
    // Registration form
    @FindBy(id = "id_gender1")
    private WebElement genderMale;

    @FindBy(id = "customer_firstname")
    private WebElement firstName;

    @FindBy(id = "customer_lastname")
    private WebElement lastName;
    
    @FindBy(id = "email")
    private WebElement regEmail;

    @FindBy(id = "passwd")
    private WebElement regpassword;

    // Date of Birth - Day
    @FindBy(id = "days")
    private WebElement dobDay;

    // Date of Birth - Month
    @FindBy(id = "months")
    private WebElement dobMonth;

    // Date of Birth - Year
    @FindBy(id = "years")
    private WebElement dobYear;

    
    @FindBy(id = "newsletter")
    private WebElement Checkbox1;
    
    @FindBy(id = "optin")
    private WebElement Checkbox2;

    @FindBy(id = "submitAccount")
    private WebElement registerButton;
    

    // Login Email field
    @FindBy(id = "email")
    private WebElement loginEmail;

    // Password field
    @FindBy(id = "passwd")
    private WebElement loginPassword;

    // Login button
    @FindBy(id = "SubmitLogin")
    private WebElement loginButton;

    // Success message (My account)
    @FindBy(className = "info-account")
    private WebElement successMessage;

    // Error message
    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement errorMessage;

    public void openLoginPage() {
        signInLink.click();
    }
    
    public void createAccount(String email, String pwd) {
        emailCreate.sendKeys(email);
        createAccountButton.click();
        wait.until(ExpectedConditions.visibilityOf(genderMale)).click();
        genderMale.click();
        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        regpassword.sendKeys(pwd);
        
        new Select(dobDay).selectByValue("10");
        new Select(dobMonth).selectByValue("5");
        new Select(dobYear).selectByValue("1998");
        
        Checkbox1.click();
        Checkbox2.click();
        registerButton.click();
        
    } 

    public void login(String email, String password) {
        loginEmail.clear();
        loginEmail.sendKeys(email);
        loginPassword.clear();
        loginPassword.sendKeys(password);
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
    	    return wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();

    }

    public boolean isLoginFailed() {
    	return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
    }
}*/

package com.selenium.selenium_maven.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Sign in link (only visible when logged out)
    @FindBy(className = "login")
    private WebElement signInLink;

    // Email & Password (used for both login & registration)
    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "passwd")
    private WebElement password;

    // Create account
    @FindBy(id = "email_create")
    private WebElement emailCreate;

    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;

    // Registration form
    @FindBy(id = "id_gender1")
    private WebElement genderMale;

    @FindBy(id = "customer_firstname")
    private WebElement firstName;

    @FindBy(id = "customer_lastname")
    private WebElement lastName;

    @FindBy(id = "days")
    private WebElement dobDay;

    @FindBy(id = "months")
    private WebElement dobMonth;

    @FindBy(id = "years")
    private WebElement dobYear;

    @FindBy(id = "newsletter")
    private WebElement checkbox1;

    @FindBy(id = "optin")
    private WebElement checkbox2;

    @FindBy(id = "submitAccount")
    private WebElement registerButton;

    // Login button
    @FindBy(id = "SubmitLogin")
    private WebElement loginButton;

    // Success message
    @FindBy(className = "info-account")
    private WebElement successMessage;

    // Error message
    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement errorMessage;

    // ✅ SAFE login page navigation
    public void openLoginPage() {
        try {
            if (signInLink.isDisplayed()) {
                signInLink.click();
            }
        } catch (Exception e) {
            // already logged in
        }
    }

    public void createAccount(String userEmail, String pwd) {
        emailCreate.sendKeys(userEmail);
        createAccountButton.click();

        wait.until(ExpectedConditions.visibilityOf(genderMale)).click();
        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        password.sendKeys(pwd);

        new Select(dobDay).selectByValue("10");
        new Select(dobMonth).selectByValue("5");
        new Select(dobYear).selectByValue("1998");

        checkbox1.click();
        checkbox2.click();
        registerButton.click();
    }

    public void login(String userEmail, String pwd) {
        email.clear();
        email.sendKeys(userEmail);
        password.clear();
        password.sendKeys(pwd);
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
    }

    public boolean isLoginFailed() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
    }
}

