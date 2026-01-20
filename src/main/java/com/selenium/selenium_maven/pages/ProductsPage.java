package com.selenium.selenium_maven.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.selenium.selenium_maven.models.ProductRow;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage {

    WebDriver driver;
    Actions actions;

    @FindBy(id = "selectProductSort")
    WebElement sortDropdown;

    @FindBy(xpath = "//span[@class='category-name']")
    WebElement categoryName;

    @FindBy(xpath = "//ul[contains(@class,'product_list')]/li")
    List<WebElement> products;

    @FindBy(xpath = "//div[@class='pagination']")
    List<WebElement> pagination;
    

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /* ---------- WAIT ---------- */
    public void waitForProductsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(products));
    }

    public void waitForTshirtsCategory() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(
                categoryName, "T-shirts"));
    }

    /* ---------- CATEGORY ---------- */
    public boolean isTshirtsCategoryDisplayed() {
        return categoryName.isDisplayed()
                && categoryName.getText().equalsIgnoreCase("T-shirts");
    }
    
    

    /* ---------- SORT ---------- */
    public void sortByPriceLowToHigh() {
        new Select(sortDropdown).selectByValue("price:asc");
        
    }

    /* ---------- PRODUCT ROW PARSING ---------- */
    public List<ProductRow> getProductRows() {

        List<ProductRow> rows = new ArrayList<>();

        for (WebElement product : products) {

            String name = product
                    .findElement(By.xpath(".//a[@class='product-name']"))
                    .getText();

            double price = getVisibleProductPrice(product);
            boolean inStock = isProductInStock(product);

            rows.add(new ProductRow(name, price, inStock));
        }
        return rows;
    }

    private double getVisibleProductPrice(WebElement product) {

        List<WebElement> prices =
                product.findElements(By.xpath(".//span[contains(@class,'product-price')]"));

        for (WebElement price : prices) {
            if (price.isDisplayed()) {
                return Double.parseDouble(
                        price.getText().replace("$", "").trim()
                );
            }
        }
        throw new RuntimeException("Visible price not found");
    }

    public boolean isPriceSortedAscendingProperly() {

        List<ProductRow> rows = getProductRows();
        List<Double> actualPrices = new ArrayList<>();

        for (ProductRow row : rows) {
            actualPrices.add(row.getPrice());
        }
        

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        return actualPrices.equals(sortedPrices);
    }

    public void validatePriceAndStockData() {

        List<ProductRow> rows = getProductRows();

        for (ProductRow row : rows) {
            Assert.assertTrue(row.getPrice() > 0,
                    "Invalid price for product: " + row.getName());

            System.out.println(
                    "Product: " + row.getName() +
                    " | Price: " + row.getPrice() +
                    " | In Stock: " + row.isInStock()
            );
        }
    }

    
    /* ---------- 🔥 OUT OF STOCK (IMPORTANT) ---------- */

    private boolean isProductInStock(WebElement product) {
        List<WebElement> outOfStock =
                product.findElements(By.xpath(".//span[contains(text(),'Out of stock')]"));
        return outOfStock.isEmpty();
    }

    // ✅ USED BY TEST TO CAPTURE SCREENSHOT
    public boolean isFirstProductOutOfStock() {
        WebElement firstProduct = products.get(0);

        List<WebElement> outOfStock =
                firstProduct.findElements(By.xpath(".//span[contains(text(),'Out of stock')]"));

        return !outOfStock.isEmpty();
    }

 // ✅ USE THIS FOR SCREENSHOT (STABLE CARD)
    public WebElement getFirstProductCard() {
        return products.get(0)
                .findElement(By.cssSelector("div.product-container"));
    }

    /* ---------- PAGINATION ---------- */
    public void validatePaginationAcrossPages() {

        if (pagination.size() == 0) {
            System.out.println("Pagination not present");
            return;
        }

        List<WebElement> pages =
                driver.findElements(By.xpath("//ul[@class='pagination']//a"));

        for (int i = 0; i < pages.size(); i++) {

            pages.get(i).click();
            Assert.assertFalse(getProductRows().isEmpty(),
                    "No products found on page " + (i + 1));
            driver.navigate().back();
        }
    }

    /* ---------- QUICK VIEW ---------- */
    public void hoverAndClickQuickView() {

        WebElement firstProduct = products.get(0);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", firstProduct);

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        actions.moveToElement(firstProduct).perform();

        WebElement quickView =
                firstProduct.findElement(By.className("quick-view"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(quickView));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", quickView);
    }

    /* ---------- NEW TAB ---------- */
    public void openFirstProductInNewTab() {

        String link = products.get(0)
                .findElement(By.xpath(".//a[@class='product-name']"))
                .getAttribute("href");

        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0])", link);
    }
}


/*package com.selenium.selenium_maven.pages;

import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.testng.Assert;

public class ProductsPage {

    WebDriver driver;
    Actions actions;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /* ---------- SEARCH ---------- */

    /*@FindBy(id = "search_product")
    private WebElement searchBox;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(css = ".productinfo")
    private List<WebElement> searchResults;

    public void searchProduct(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchButton.click();
    }

    public int getProductCount() {
        return searchResults.size();
    }

    /* ---------- CATEGORY (AutomationExercise uses links) ---------- */

   /* @FindBy(xpath = "//a[text()='Women']")
    private WebElement womenCategory;

    public void selectWomenCategory() {
        womenCategory.click();
    }

    /* ---------- PRICE & STOCK VALIDATION ---------- */

    /*public void validatePriceAndStock() {

        for (WebElement product : searchResults) {

            String priceText =
                    product.findElement(By.tagName("h2"))
                           .getText().replace("Rs. ", "");

            int price = Integer.parseInt(priceText);
            Assert.assertTrue(price > 0, "Invalid price found");

            System.out.println("Price validated: Rs. " + price);
        }
    }

    /* ---------- ADD TO CART ---------- */

    /*public void addFirstProductToCart() {

        WebElement firstProduct = searchResults.get(0);
        actions.moveToElement(firstProduct).perform();

        firstProduct.findElement(By.linkText("Add to cart")).click();
    }

    public boolean isCartModalDisplayed() {
        return driver.findElement(
                By.xpath("//div[@class='modal-content']")).isDisplayed();
    }

    /* ---------- NEW TAB ---------- */

    /*public void openFirstProductInNewTab() {

        String productLink =
                searchResults.get(0)
                .findElement(By.tagName("a"))
                .getAttribute("href");

        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0])", productLink);
    }

    /* ---------- ACTIONS ---------- */

    /*public void hoverOnFirstProduct() {
        actions.moveToElement(searchResults.get(0)).perform();
    }
}*/


