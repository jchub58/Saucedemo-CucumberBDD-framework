package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;

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
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    public List<WebElement> getAllProducts() {
        return driver.findElements(productItems);
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void addProductToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(buttonId)).click();
    }

    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }

    public void sortBy(String option) {
        driver.findElement(sortDropdown).click();
        driver.findElement(By.cssSelector("option[value='" + option + "']")).click();
    }
}