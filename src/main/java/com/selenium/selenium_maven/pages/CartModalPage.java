package com.selenium.selenium_maven.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartModalPage {

    @FindBy(xpath = "//h2[contains(text(),'Product successfully added')]")
    WebElement successMsg;

    @FindBy(xpath = "//span[@title='Close window']")
    WebElement closeBtn;

    public CartModalPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessDisplayed() {
        return successMsg.isDisplayed();
    }

    public void closeModal() {
        closeBtn.click();
    }
}
