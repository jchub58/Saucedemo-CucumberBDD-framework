package com.saucedemo.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.utils.ConfigReader;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = ConfigReader.getInt("explicit.wait");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }


    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }


    protected void waitAndClick(By locator) {
        waitForClickable(locator).click();
    }

    protected void waitAndSendKeys(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String waitAndGetText(By locator) {
        return waitForElement(locator).getText();
    }

    // JavaScript utilities
    protected void jsClick(By locator) {
        WebElement element = waitForElement(locator);
        js.executeScript("arguments[0].click();", element);
    }

    protected void jsScrollToElement(By locator) {
        WebElement element = waitForElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsScrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void jsScrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    protected void jsHighlight(By locator) {
        WebElement element = waitForElement(locator);
        js.executeScript(
                "arguments[0].style.border='3px solid red';", element);
    }

    
    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitForElement(locator));
        select.selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        Select select = new Select(waitForElement(locator));
        select.selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        Select select = new Select(waitForElement(locator));
        select.selectByIndex(index);
    }

    // Actions utilities
    protected void hoverOver(By locator) {
        WebElement element = waitForElement(locator);
        actions.moveToElement(element).perform();
    }

    protected void doubleClick(By locator) {
        WebElement element = waitForElement(locator);
        actions.doubleClick(element).perform();
    }

    protected void rightClick(By locator) {
        WebElement element = waitForElement(locator);
        actions.contextClick(element).perform();
    }

    protected void dragAndDrop(By source, By target) {
        WebElement sourceElement = waitForElement(source);
        WebElement targetElement = waitForElement(target);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    // Alert handling
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    protected String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    // Frame handling
    protected void switchToFrame(By locator) {
        WebElement frame = waitForElement(locator);
        driver.switchTo().frame(frame);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // Window handling
    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    // Element state utilities
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }
}