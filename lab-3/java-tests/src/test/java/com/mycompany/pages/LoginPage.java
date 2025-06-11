package com.mycompany.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mycompany.util.AppUrls;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement emailField;
    
    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;
    
    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div[2]/main/div/div/form/button")
    private WebElement loginButton;
    
    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div[2]/main/div/div/form/div[4]/div/p/span")
    private WebElement loginErrorMessage;

    @FindBy(xpath = "//*[@id=\"uc-center-container\"]/div[2]/div/div/div/button[2]")
    private WebElement acceptAllPrivacyButton;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(AppUrls.LOGIN);
        PageFactory.initElements(driver, this);
    }

    public LoginPage acceptAllPrivacy() {
        try {
            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#usercentrics-root")
            ));
            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement acceptButton = shadowRoot.findElement(
                By.cssSelector("#uc-center-container > div.sc-eBMEME.gMirTG > div > div > div > button.sc-dcJsrY.eLOIWU")
            );
            acceptButton.click();
            wait.until(driver1 -> !shadowHost.isDisplayed());
        } catch (Exception e) {
        }

        return this;
    }

    
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clearFormFields() {
        emailField.clear();
        passwordField.clear();
    }
    
    public void clickLogin() {
        loginButton.click();
    }
    
    public String getLoginErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));
        return loginErrorMessage.getText();
    }
    
    public boolean isLoginPageDisplayed() {
        return emailField.isDisplayed() && passwordField.isDisplayed();
    }
    
    public HomePage loginWithValidCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new HomePage(driver);
    }
}