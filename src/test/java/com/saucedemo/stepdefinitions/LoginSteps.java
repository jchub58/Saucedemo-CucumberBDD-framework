package com.saucedemo.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;

public class LoginSteps {
    WebDriver driver;

    @Given("I am on the SauceDemo login page")
    public void iAmOnTheSauceDemoLoginPage() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("I click the login button")
    public void iClickTheLoginButton() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("I should be redirected to the products page")
    public void iShouldBeRedirectedToTheProductsPage() {
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

@And("I should see {string} as the page title")
    public void iShouldSeeAsThePageTitle(String title) {
        String actualTitle = driver.findElement(By.className("title")).getText();
        assertEquals(title, actualTitle);
        driver.quit();
    }
    
    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }
    
    @And("the error should contain {string}")
    public void theErrorShouldContain(String errorText) {
        String error = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        assertTrue(error.contains(errorText));
        driver.quit();
    }
}

}