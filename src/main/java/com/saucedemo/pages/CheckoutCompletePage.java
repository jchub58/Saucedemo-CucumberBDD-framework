package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private By pageTitle = By.className("title");
    private By completeHeader = By.className("complete-header");
    private By completeText = By.className("complete-text");
    private By backHomeButton = By.id("back-to-products");
    private By ponyExpressImage = By.className("pony-express");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitAndGetText(pageTitle);
    }

    public String getConfirmationMessage() {
        return waitAndGetText(completeHeader);
    }

    public String getCompleteText() {
        return waitAndGetText(completeText);
    }

    public void clickBackHome() {
        waitAndClick(backHomeButton);
    }

    public boolean isPonyExpressImageDisplayed() {
        return isElementDisplayed(ponyExpressImage);
    }

    public boolean isOrderComplete() {
        return isElementDisplayed(completeHeader) && 
               getConfirmationMessage().contains("Thank you for your order");
    }
}
