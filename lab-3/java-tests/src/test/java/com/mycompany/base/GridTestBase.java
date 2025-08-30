package com.mycompany.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class GridTestBase {
    protected WebDriver driver;
    private static final String GRID_HUB_URL = "http://localhost:4444/wd/hub";

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser) throws MalformedURLException {
        MutableCapabilities options;

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                options = firefoxOptions;
                break;
            case "edge":
                options = new EdgeOptions();
                // chromeOptions.addArguments("--start-maximized");
                // options = chromeOptions;
                break;
            default:
                options = new FirefoxOptions();
                // ChromeOptions defaultOptions = new ChromeOptions();
                // defaultOptions.addArguments("--start-maximized");
                // options = defaultOptions;
        }

        options.setCapability("timeouts", Map.of(
            "implicit", 30000,
            "pageLoad", 300000,
            "script", 30000
        ));

        System.out.println("Connecting to Grid hub: " + GRID_HUB_URL);
        System.out.println("Browser: " + browser + ", Options: " + options);

        try {
            driver = new RemoteWebDriver(new URL(GRID_HUB_URL), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("Successfully connected to Grid");
        } catch (Exception e) {
            System.err.println("Failed to connect to Grid: " + e.getMessage());
            throw e;
        }
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}