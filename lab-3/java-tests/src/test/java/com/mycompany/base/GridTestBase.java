package com.mycompany.base;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class GridTestBase {
    private WebDriver driver;
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

    public String getGridHubUrl() {
        return GRID_HUB_URL;    
    }

    protected void takeScreenshot(String name) {
        if (getDriver() instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }

    @Attachment(value = "Environment Info", type = "text/plain")
    protected String allureAddEnvironmentInfo(String key, String value) {
        return key + ": " + value;
    }

    @Attachment(value = "Execution Log", type = "text/plain")
    protected String allureAddLog(String message) {
        return message;
    }
}