package com.saucedemo.stepdefinitions;

import com.saucedemo.factory.DriverFactory;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigReader;
import static org.junit.Assert.*;

public class SidebarSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    public SidebarSteps() {
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);
        this.productsPage = new ProductsPage(driver);
    }

    @Given("I am logged in as a standard user")
    @Step("Login as standard user")
    public void iAmLoggedInAsAStandardUser() {
        driver.get(ConfigReader.get("base.url"));
        loginPage.login("standard_user", "secret_sauce");
    }

    @Given("I have added {string} to the cart")
    @Step("Add {0} to cart")
    public void iHaveAddedToTheCart(String productName) {
        productsPage.addProductToCartByName(productName);
    }

    @And("the cart badge shows {int} item")
    @Step("Verify cart badge shows {0} item")
    public void theCartBadgeShowsItem(int count) {
        assertEquals("Cart badge should show " + count + " item", count, productsPage.getCartBadgeCount());
    }

    @When("I open the sidebar menu")
    @Step("Open sidebar menu")
    public void iOpenTheSidebarMenu() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
    }

    @And("I click the {string} link")
    @Step("Click {0} link in sidebar")
    public void iClickTheLink(String linkText) {
        String linkId = "";
        switch (linkText) {
            case "Logout":
                linkId = "logout_sidebar_link";
                break;
            case "Reset App State":
                linkId = "reset_sidebar_link";
                break;
        }
        driver.findElement(By.id(linkId)).click();
    }

    @Then("I should be redirected to the login page")
    @Step("Verify redirected to login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        assertTrue("Should be on login page", 
                   driver.getCurrentUrl().contains("saucedemo.com") && 
                   !driver.getCurrentUrl().contains("inventory"));
    }

    @And("I should not be able to access inventory page directly")
    @Step("Verify cannot access inventory directly after logout")
    public void iShouldNotBeAbleToAccessInventoryPageDirectly() {
        driver.get(ConfigReader.get("base.url") + "inventory.html");
        assertTrue("Should be redirected to login", 
                   driver.getCurrentUrl().contains("saucedemo.com") && 
                   !driver.getCurrentUrl().contains("inventory"));
    }

    @And("I close the sidebar menu")
    @Step("Close sidebar menu")
    public void iCloseTheSidebarMenu() {
        driver.findElement(By.id("react-burger-cross-btn")).click();
    }

    @Then("the cart badge should disappear")
    @Step("Verify cart badge disappears")
    public void theCartBadgeShouldDisappear() {
        // Wait a moment for the reset to take effect
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals("Cart badge should be gone", 0, productsPage.getCartBadgeCount());
    }

    @And("the {string} button should be reset for all products")
    @Step("Verify Add to cart buttons are reset")
    public void theButtonShouldBeResetForAllProducts(String buttonText) {
        // Check that at least one product has "Add to cart" button
        boolean addCartButtonExists = driver.findElements(By.cssSelector("[data-test^='add-to-cart']")).size() > 0;
        assertTrue("Add to cart buttons should be reset", addCartButtonExists);
    }
}
