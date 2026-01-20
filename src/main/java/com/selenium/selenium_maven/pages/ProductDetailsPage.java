package com.selenium.selenium_maven.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage {
	private WebDriver driver;

	private WebDriverWait wait;

    @FindBy(xpath = "//h1")
    WebElement title;

    @FindBy(css = ".breadcrumb")
    WebElement breadcrumb;

    @FindBy(name = "Submit")
    WebElement addToCartBtn;

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /* ---------- VERIFICATIONS ---------- */

    public boolean isBreadcrumbVisible() {
        wait.until(ExpectedConditions.visibilityOf(breadcrumb));
        return breadcrumb.isDisplayed();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText();
    }
    
    public boolean isInStock() {
        try {
            return addToCartBtn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /* ---------- ACTIONS ---------- */

    public void addToCart() {
        if (!isInStock()) {
            throw new RuntimeException("Add to cart attempted for OUT OF STOCK product");
        }
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        addToCartBtn.click();
    }
}