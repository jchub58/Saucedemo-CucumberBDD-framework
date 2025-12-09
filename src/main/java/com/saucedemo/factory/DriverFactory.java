package com.saucedemo.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import com.saucedemo.utils.ConfigReader;
import java.time.Duration;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            case "edge":
                driver.set(new EdgeDriver());
                break;
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        getDriver().manage().window().maximize();

        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}