package com.selenium.selenium_maven.actions;

import org.openqa.selenium.WebDriver;
import com.selenium.selenium_maven.pages.HomePage;

public class SearchActions {

    HomePage homePage;

    public SearchActions(WebDriver driver) {
        homePage = new HomePage(driver);
    }

    public void searchForProduct(String productName) {
        homePage.enterSearchKeyword(productName);
        homePage.clickSearchButton();
    }

    public boolean validateSearchResults() {
        return homePage.isSearchResultDisplayed();
    }
}
