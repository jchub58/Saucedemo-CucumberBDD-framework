package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        waitAndSendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        waitAndSendKeys(passwordField, password);
    }

    public void clickLogin() {
        waitAndClick(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorDisplayed() {
        return isElementPresent(errorMessage);
    }

    public String getErrorMessage() {
        return waitAndGetText(errorMessage);
    }
}