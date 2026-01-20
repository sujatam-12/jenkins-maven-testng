package com.selenium.selenium_maven.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.selenium.selenium_maven.base.BaseTest;
import com.selenium.selenium_maven.pages.*;
import com.selenium.selenium_maven.utils.ScreenshotUtil;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndProductTest extends BaseTest {

    @Test
    public void E2E_Product_Flow() {
    	ExtentTest test = extent.createTest("E2E Product Flow");
    	
    	

        // ---------- PAGE OBJECTS ----------
        HomePage home = new HomePage(driver);
        ProductsPage products = new ProductsPage(driver);

        /* ---------- CATEGORY NAVIGATION ---------- */
        test.log(Status.INFO, "Navigating to T-Shirts category");
        home.goToTshirts();
        products.waitForTshirtsCategory();   // 🔑 FIREFOX FIX
        products.waitForProductsToLoad();

        Assert.assertTrue(
                products.isTshirtsCategoryDisplayed(),
                "Category filter failed"
        );
        test.log(Status.PASS, "T-Shirts category verified");

        /* ---------- SORTING ---------- */
        test.log(Status.INFO, "Sorting products by price (Low to High)");
        products.sortByPriceLowToHigh();

        Assert.assertTrue(
                products.isPriceSortedAscendingProperly(),
                "Price sorting failed"
        );

        test.log(Status.PASS, "Price sorting verified successfully");


        /* ---------- PRICE + STOCK (TABLE / GRID PARSING) ---------- */
        test.log(Status.INFO, "Validating product price and stock");
        products.validatePriceAndStockData();

        /* ---------- PAGINATION ---------- */
        test.log(Status.INFO, "Validating pagination if present");
        products.validatePaginationAcrossPages();
        
        

        /* ---------- OUT OF STOCK VALIDATION (LISTING PAGE) ---------- */
        if (products.isFirstProductOutOfStock()) {

            test.log(Status.INFO, "Product is OUT OF STOCK on listing page");

            WebElement productCard = products.getFirstProductCard();

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    productCard
            );

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(productCard));

            String screenshotPath =
                    ScreenshotUtil.takeElementScreenshot(
                            driver,
                            productCard,
                            "OUT_OF_STOCK_PRODUCT"
                    );

            test.addScreenCaptureFromPath(
                    screenshotPath,
                    "Out of Stock product card"
            );

            System.out.println("Out of Stock screenshot captured");

        } else {

            test.log(Status.INFO, "Product is IN STOCK on listing page");

            products.openFirstProductInNewTab();

            String parentWindow = driver.getWindowHandle();

            for (String win : driver.getWindowHandles()) {
                if (!win.equals(parentWindow)) {
                    driver.switchTo().window(win);
                    break;
                }
            }

            ProductDetailsPage details = new ProductDetailsPage(driver);
            details.addToCart();

            CartModalPage cart = new CartModalPage(driver);
            Assert.assertTrue(cart.isSuccessDisplayed(), "Add to cart failed");
            
            test.log(Status.INFO, "Promo iframe interaction executed");
            PromoFramePage promo = new PromoFramePage(driver);
            promo.interactWithFrameIfPresent(test);
            


            cart.closeModal();
            driver.close();
            driver.switchTo().window(parentWindow);
        }
        
        

        /* ---------- QUICK VIEW FLOW ---------- */
        test.log(Status.INFO, "Hovering and opening Quick View");
        products.hoverAndClickQuickView();

        QuickViewPage quickView = new QuickViewPage(driver);
        Assert.assertTrue(
                quickView.isQuickViewVisible(),
                "Quick View not visible"
        );
        test.log(Status.PASS, "Quick View opened successfully");

        quickView.closeQuickView();
        test.log(Status.INFO, "Quick View closed");
        
        
        
     // ---------- OPEN PRODUCT IN NEW TAB ----------
        test.log(Status.INFO, "Opening product in new tab");
        products.openFirstProductInNewTab();

        String parentWindow = driver.getWindowHandle();

        // Switch to new tab
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(parentWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }
     

        // ---------- PRODUCT DETAILS ASSERTION ----------
        ProductDetailsPage details1 = new ProductDetailsPage(driver);

        Assert.assertTrue(
                details1.isBreadcrumbVisible(),
                "Breadcrumb not visible on product details page"
        );

        Assert.assertFalse(
                details1.getTitle().isEmpty(),
                "Product title is empty"
        );

        test.log(Status.PASS, "Product title and breadcrumb verified");
        
     // ---------- SWITCH BACK ----------
        driver.close(); // close product tab
        driver.switchTo().window(parentWindow);

        test.log(Status.INFO, "Switched back to main window");


        /* ---------- JS ALERT HANDLING ---------- */
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {}
    }
}
