package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    private By cartItems = By.className("cart_item");
    private By itemNames = By.className("inventory_item_name");
    private By itemPrices = By.className("inventory_item_price");
    private By removeButtons = By.cssSelector("[data-test^='remove']");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        return driver.findElements(itemNames).stream()
                .map(WebElement::getText)
                .toList();
    }

    public void removeItem(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(buttonId)).click();
    }

    public void proceedToCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public void continueShopping() {
        driver.findElement(continueShoppingButton).click();
    }
}