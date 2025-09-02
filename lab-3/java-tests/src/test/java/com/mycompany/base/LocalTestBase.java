package com.mycompany.base;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;


public class LocalTestBase {
    private WebDriver driver;
        
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
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