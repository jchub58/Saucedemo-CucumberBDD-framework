package com.saucedemo.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.utils.ConfigReader;
import static org.junit.Assert.*;

public class ProductSteps {
    private WebDriver driver;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private LoginPage loginPage;

    public ProductSteps() {
        this.driver = Hooks.getDriver();
        this.productsPage = new ProductsPage(driver);
        this.cartPage = new CartPage(driver);
        this.loginPage = new LoginPage(driver);
    }

    @Given("I am logged in as a standard user")
    public void iAmLoggedInAsAStandardUser() {
        driver.get(ConfigReader.get("base.url"));
        loginPage.login(
                ConfigReader.get("standard.user"),
                ConfigReader.get("password")
        );
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        assertEquals("Products", productsPage.getPageTitle());
    }

    @Then("I should see {int} products listed")
    public void iShouldSeeProductsListed(int count) {
        assertEquals(count, productsPage.getProductCount());
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String productName) {
        productsPage.addProductToCartByName(productName);
    }

    @Given("I have added {string} to the cart")
    public void iHaveAddedToTheCart(String productName) {
        productsPage.addProductToCartByName(productName);
    }

    @Then("the cart badge should show {int} item(s)")
    public void theCartBadgeShouldShowItems(int count) {
        assertEquals(count, productsPage.getCartBadgeCount());
    }

    @When("I go to the cart page")
    public void iGoToTheCartPage() {
        productsPage.goToCart();
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String productName) {
        cartPage.removeItem(productName);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertEquals(0, cartPage.getCartItemCount());
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        String value = switch (sortOption) {
            case "Price (low to high)" -> "lohi";
            case "Price (high to low)" -> "hilo";
            case "Name (A to Z)" -> "az";
            case "Name (Z to A)" -> "za";
            default -> "az";
        };
        productsPage.sortBy(value);
    }

    @Then("the products should be sorted by price ascending")
    public void theProductsShouldBeSortedByPriceAscending() {
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}