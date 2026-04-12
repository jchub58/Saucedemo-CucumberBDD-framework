package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    private By pageTitle = By.className("title");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By itemTotal = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By cartItems = By.className("cart_item");
    private By itemNames = By.className("inventory_item_name");
    private By itemPrices = By.className("inventory_item_price");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitAndGetText(pageTitle);
    }

    public void clickFinish() {
        waitAndClick(finishButton);
    }

    public void clickCancel() {
        waitAndClick(cancelButton);
    }

    public String getItemTotal() {
        return waitAndGetText(itemTotal);
    }

    public String getTax() {
        return waitAndGetText(taxLabel);
    }

    public String getTotal() {
        return waitAndGetText(totalLabel);
    }

    public int getCartItemCount() {
        return getElements(cartItems).size();
    }

    public boolean isOrderSummaryDisplayed() {
        return isElementDisplayed(itemTotal) && isElementDisplayed(taxLabel) && isElementDisplayed(totalLabel);
    }

    public double extractPrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", ""));
    }

    public double calculateExpectedTotal(double itemTotal, double tax) {
        return itemTotal + tax;
    }
}
