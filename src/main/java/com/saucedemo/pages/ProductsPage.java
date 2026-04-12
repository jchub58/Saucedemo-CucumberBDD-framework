package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsPage extends BasePage {
    private By pageTitle = By.className("title");
    private By productItems = By.className("inventory_item");
    private By productNames = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");
    private By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private By removeButtons = By.cssSelector("[data-test^='remove']");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By sortDropdown = By.className("product_sort_container");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitAndGetText(pageTitle);
    }

    public int getProductCount() {
        return getElements(productItems).size();
    }

    public List<WebElement> getAllProducts() {
        return getElements(productItems);
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> buttons = getElements(addToCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void addProductToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        waitAndClick(By.id(buttonId));
    }

    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(waitAndGetText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        waitAndClick(cartLink);
    }

    public void sortBy(String option) {
        waitAndClick(sortDropdown);
        waitAndClick(By.cssSelector("option[value='" + option + "']"));
    }
}