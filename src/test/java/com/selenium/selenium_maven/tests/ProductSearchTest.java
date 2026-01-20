/*package com.selenium.selenium_maven.tests;

import com.selenium.selenium_maven.base.BaseTest;
import com.selenium.selenium_maven.pages.HomePage;
import com.selenium.selenium_maven.pages.ProductsPage;
import com.selenium.selenium_maven.utils.TestDataProvider;

import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;

public class ProductSearchTest extends BaseTest {

    @Test(dataProvider = "searchData", dataProviderClass = TestDataProvider.class)
    public void validateProductSearch(String keyword) {

        ExtentTest test =
                extent.createTest("Search Test - " + keyword);

        HomePage home = new HomePage(driver);
        ProductsPage products = new ProductsPage(driver);

        // Launch & Navigate
        home.goToProducts();

        // Search
        products.searchProduct(keyword);
        Assert.assertTrue(products.getProductCount() > 0);
        test.pass("Search successful");

        // Category
        products.selectWomenCategory();
        test.pass("Category selected");

        // Price & Stock
        products.validatePriceAndStock();
        test.pass("Price and stock validated");

        // Add to cart
        products.addFirstProductToCart();
        Assert.assertTrue(products.isCartModalDisplayed());
        test.pass("Add to cart validated");

        // New tab
        String parent = driver.getWindowHandle();
        products.openFirstProductInNewTab();

        Set<String> windows = driver.getWindowHandles();
        for (String win : windows) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
                Assert.assertTrue(driver.getTitle().length() > 0);
                driver.close();
            }
        }
        driver.switchTo().window(parent);
        test.pass("New tab validation successful");

        // Hover
        products.hoverOnFirstProduct();
        test.pass("Hover interaction validated");
    }
}*/
