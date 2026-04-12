package com.saucedemo.stepdefinitions;

import com.saucedemo.factory.DriverFactory;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import static org.junit.Assert.*;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;

    public LoginSteps() {
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);
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
    @Step("Verify user is redirected to products page")
    public void iShouldBeRedirectedToTheProductsPage() {
        assertTrue("User should be on inventory page", 
                   driver.getCurrentUrl().contains("inventory"));
    }

    @And("I should see {string} as the page title")
    @Step("Verify page title is: {0}")
    public void iShouldSeeAsThePageTitle(String title) {
        String actualTitle = driver.findElement(By.className("title")).getText();
        assertEquals("Page title should match", title, actualTitle);
    }

    @Then("I should see an error message")
    @Step("Verify error message is displayed")
    public void iShouldSeeAnErrorMessage() {
        assertTrue("Error message should be displayed", loginPage.isErrorDisplayed());
    }

    @And("the error should contain {string}")
    @Step("Verify error message contains: {0}")
    public void theErrorShouldContain(String errorText) {
        assertTrue("Error should contain: " + errorText, 
                   loginPage.getErrorMessage().contains(errorText));
    }

    @Given("I try to access the inventory page directly without login")
    @Step("Try to access inventory page without authentication")
    public void iTryToAccessInventoryPageDirectlyWithoutLogin() {
        driver.get(ConfigReader.get("base.url") + "inventory.html");
    }

    @Then("I should be redirected to the login page")
    @Step("Verify user is redirected to login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        assertTrue("User should be on login page", 
                   driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @And("I should see an error message about authorization")
    @Step("Verify authorization error message")
    public void iShouldSeeAnErrorMessageAboutAuthorization() {
        assertTrue("Authorization error should be displayed", 
                   loginPage.isErrorDisplayed());
    }

    @Given("I am logged in as a standard user")
    @Step("Login as standard user")
    public void iAmLoggedInAsAStandardUser() {
        driver.get(ConfigReader.get("base.url"));
        loginPage.login("standard_user", "secret_sauce");
        assertTrue("User should be logged in", 
                   driver.getCurrentUrl().contains("inventory"));
    }
}