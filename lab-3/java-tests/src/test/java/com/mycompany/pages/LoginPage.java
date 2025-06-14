package com.mycompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.mycompany.util.AppUrls;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement emailField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div[2]/main/div/div/form/button")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div[2]/main/div/div/form/div[4]/div/p/span")
    private WebElement loginErrorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(AppUrls.LOGIN);
    }

    public LoginPage enterUsername(String username) {
        type(emailField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public LoginPage clearFormFields() {
        emailField.clear();
        passwordField.clear();
        return this;
    }

    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }

    public String getLoginErrorMessage() {
        return getText(loginErrorMessage);
    }

    public boolean isLoginPageDisplayed() {
        return isVisible(emailField) && isVisible(passwordField);
    }

    public HomePage loginWithValidCredentials(String username, String password) {
        return enterUsername(username)
            .enterPassword(password)
            .clickLogin()
            .navigateToHome();
    }

    private HomePage navigateToHome() {
        return new HomePage(super.getDriver());
    }
}
