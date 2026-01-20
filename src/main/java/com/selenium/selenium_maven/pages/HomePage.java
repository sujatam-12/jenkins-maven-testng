package com.selenium.selenium_maven.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    Actions actions;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver); // ✅ CRITICAL FIX
        PageFactory.initElements(driver, this);
        
    }

    // Search input box
    @FindBy(id = "search_query_top")
    private WebElement searchBox;

    // Search button
    @FindBy(name = "submit_search")
    private WebElement searchButton;

    // Search result section
    @FindBy(className = "product_list")
    private WebElement searchResults;
    
   @FindBy(xpath = "//a[@title='Women']")
    WebElement womenMenu;

   @FindBy(xpath = "//a[@title='T-shirts']")
   WebElement tshirtLink;
   
    public void enterSearchKeyword(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public boolean isSearchResultDisplayed() {
        return searchResults.isDisplayed();
    }
    public void goToTshirts() {

        Actions actions = new Actions(driver);

        actions
            .moveToElement(womenMenu)
            .pause(500)
            .moveToElement(tshirtLink)
            .click()
            .perform();
    }
        

    }
    
/*package com.selenium.selenium_maven.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@href='/products']")
    private WebElement productsLink;

    public void goToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(productsLink)).click();
    }
}*/

