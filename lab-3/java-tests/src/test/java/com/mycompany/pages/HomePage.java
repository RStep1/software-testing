package com.mycompany.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//*[@id=\"content\"]/div/section/div/h1")
    private WebElement welcomeMessage;
    
    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div/div[1]/header/div/div[2]/div[1]/div/div/a[4]")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[@id=\"uc-center-container\"]/div[2]/div/div/div/button[2]")
    private WebElement acceptAllPrivacyButton;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);        
    }
    
    public String getWelcomeMessage() {
        wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
        return welcomeMessage.getText();
    }

}