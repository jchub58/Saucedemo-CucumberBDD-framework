package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private By cartItems = By.className("cart_item");
    private By itemNames = By.className("inventory_item_name");
    private By itemPrices = By.className("inventory_item_price");
    private By removeButtons = By.cssSelector("[data-test^='remove']");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return getElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        return getElements(itemNames).stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public void removeItem(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase().replace(" ", "-");
        waitAndClick(By.id(buttonId));
    }

    public void proceedToCheckout() {
        waitAndClick(checkoutButton);
    }

    public void continueShopping() {
        waitAndClick(continueShoppingButton);
    }
}