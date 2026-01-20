/*package com.selenium.selenium_maven.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PromoFramePage {

    WebDriver driver;

    public PromoFramePage(WebDriver driver) {
        this.driver = driver;
    }

    public void interactWithFrameIfPresent() {
        try {
            driver.switchTo().frame(0);
            driver.findElement(By.tagName("body")).isDisplayed();
            driver.switchTo().defaultContent();
        } catch (Exception ignored) {}
    }
}*/


package com.selenium.selenium_maven.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.time.Duration;
import java.util.List;

public class PromoFramePage {

    WebDriver driver;
    WebDriverWait wait;

    public PromoFramePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void interactWithFrameIfPresent(ExtentTest test) {

        try {
            List<WebElement> frames = driver.findElements(By.tagName("iframe"));

            if (!frames.isEmpty()) {
                driver.switchTo().frame(frames.get(0));
                test.log(Status.INFO, "Switched to promo iframe");

                driver.switchTo().defaultContent();
                test.log(Status.INFO, "Exited promo iframe");
            } else {
                test.log(Status.INFO, "Promo iframe not present");
            }

        } catch (Exception e) {
            test.log(Status.WARNING, "Promo iframe interaction failed");
            driver.switchTo().defaultContent();
        }
    }

}
