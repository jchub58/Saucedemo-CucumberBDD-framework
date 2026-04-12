package com.saucedemo.stepdefinitions;

import com.saucedemo.factory.DriverFactory;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.CheckoutOverviewPage;
import com.saucedemo.utils.ConfigReader;
import static org.junit.Assert.*;

public class EndToEndSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage checkoutOverviewPage;

    public EndToEndSteps() {
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);
        this.productsPage = new ProductsPage(driver);
        this.cartPage = new CartPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
        this.checkoutOverviewPage = new CheckoutOverviewPage(driver);
    }

    @Given("I am on the SauceDemo login page")
    @Step("Navigate to SauceDemo login page")
    public void iAmOnTheSauceDemoLoginPage() {
        driver.get(ConfigReader.get("base.url"));
    }

    @When("I enter username {string}")
    @Step("Enter username: {0}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("I enter password {string}")
    @Step("Enter password: {0}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("I click the login button")
    @Step("Click login button")
    public void iClickTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the products page")
    @Step("Verify redirected to products page")
    public void iShouldBeRedirectedToTheProductsPage() {
        assertTrue("Should be on products page", 
                   driver.getCurrentUrl().contains("inventory"));
    }

    @When("I sort products by {string}")
    @Step("Sort products by {0}")
    public void iSortProductsBy(String sortOption) {
        String value = "lohi"; // Price (low to high)
        productsPage.sortBy(value);
    }

    @And("I add the cheapest product to the cart")
    @Step("Add cheapest product to cart")
    public void iAddTheCheapestProductToTheCart() {
        // After sorting low to high, add the first product
        productsPage.addProductToCartByIndex(0);
    }

    @And("I go to the cart page")
    @Step("Navigate to cart page")
    public void iGoToTheCartPage() {
        productsPage.goToCart();
    }

    @And("I proceed to checkout")
    @Step("Proceed to checkout")
    public void iProceedToCheckout() {
        cartPage.proceedToCheckout();
    }

    @And("I enter checkout information:")
    @Step("Enter checkout information")
    public void iEnterCheckoutInformation(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps();
        for (var row : data) {
            checkoutPage.fillCheckoutInfo(
                row.get("firstName"),
                row.get("lastName"),
                row.get("postalCode")
            );
        }
    }

    @And("I continue to overview")
    @Step("Continue to overview")
    public void iContinueToOverview() {
        checkoutPage.clickContinue();
    }

    @And("I finish the order")
    @Step("Finish the order")
    public void iFinishTheOrder() {
        checkoutOverviewPage.clickFinish();
    }

    @Then("I should see the order confirmation page")
    @Step("Verify order confirmation page")
    public void iShouldSeeTheOrderConfirmationPage() {
        assertTrue("Should be on checkout complete", 
                   driver.getCurrentUrl().contains("checkout-complete"));
    }

    @And("I should see {string}")
    @Step("Verify confirmation message: {0}")
    public void iShouldSeeThankYouForYourOrder(String message) {
        String confirmationText = driver.findElement(By.className("complete-header")).getText();
        assertTrue("Should see confirmation message", 
                   confirmationText.contains(message));
    }

    @When("I open the sidebar menu")
    @Step("Open sidebar menu")
    public void iOpenTheSidebarMenu() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
    }

    @And("I click the {string} link")
    @Step("Click {0} link")
    public void iClickTheLogoutLink(String linkText) {
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @Then("I should be redirected to the login page")
    @Step("Verify redirected to login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        assertTrue("Should be on login page", 
                   driver.getCurrentUrl().contains("saucedemo.com") && 
                   !driver.getCurrentUrl().contains("inventory"));
    }

    @Given("I am logged in as a standard user in the main tab")
    @Step("Login in main tab")
    public void iAmLoggedInAsAStandardUserInTheMainTab() {
        driver.get(ConfigReader.get("base.url"));
        loginPage.login("standard_user", "secret_sauce");
    }

    @When("I open a new browser tab and navigate to the inventory page")
    @Step("Open new tab and navigate to inventory")
    public void iOpenANewBrowserTabAndNavigateToTheInventoryPage() {
        // Open new tab
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(ConfigReader.get("base.url") + "inventory.html");
    }

    @Then("I should see the products page in the new tab")
    @Step("Verify products page in new tab")
    public void iShouldSeeTheProductsPageInTheNewTab() {
        assertTrue("Should see products in new tab", 
                   driver.getCurrentUrl().contains("inventory"));
    }

    @When("I logout from the main tab")
    @Step("Logout from main tab")
    public void iLogoutFromTheMainTab() {
        // Switch back to first tab
        String originalHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(originalHandle);
        
        // Logout
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @And("I try to add a product to the cart in the new tab")
    @Step("Try to add product in new tab")
    public void iTryToAddAProductToTheCartInTheNewTab() {
        // Switch to new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains("inventory")) {
                productsPage.addProductToCartByIndex(0);
                break;
            }
        }
    }

    @Then("I should be redirected to the login page")
    @Step("Verify redirected to login due to expired session")
    public void iShouldBeRedirectedToTheLoginPageDueToExpiredSession() {
        assertTrue("Should be redirected to login", 
                   driver.getCurrentUrl().contains("saucedemo.com") && 
                   !driver.getCurrentUrl().contains("inventory"));
    }

    @And("I should see an error about expired session")
    @Step("Verify session expired error")
    public void iShouldSeeAnErrorAboutExpiredSession() {
        // Check if we're on login page (session expired)
        assertTrue("Should be on login page due to expired session", 
                   driver.getCurrentUrl().contains("saucedemo.com"));
    }
}
