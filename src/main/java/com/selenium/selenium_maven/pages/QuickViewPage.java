package com.selenium.selenium_maven.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class QuickViewPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "iframe.fancybox-iframe")
    WebElement quickViewFrame;

    @FindBy(xpath = "//h1")
    WebElement productTitle;

    @FindBy(className = "fancybox-close")
    WebElement closeButton;

    public QuickViewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isQuickViewVisible() {

        // ✅ wait for iframe to appear
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(quickViewFrame));

        // ✅ wait for title inside iframe
        wait.until(ExpectedConditions.visibilityOf(productTitle));

        return productTitle.isDisplayed();
    }

    public void closeQuickView() {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}