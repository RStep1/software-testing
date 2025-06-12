package com.mycompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.mycompany.util.AppUrls;

public class SignUpPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField;

    @FindBy(xpath = "//*[@id=\"lastName\"]")
    private WebElement lastNameField;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement emailField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div[2]/main/div/form/div[3]/button")
    private WebElement nextButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public static SignUpPage open(WebDriver driver) {
        driver.get(AppUrls.SIGNUP);
        return new SignUpPage(driver);
    }

    public SignUpPage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        return this;
    }

    public SignUpPage enterLastName(String lastName) {
        type(lastNameField, lastName);
        return this;
    }

    public SignUpPage enterUsername(String username) {
        type(emailField, username);
        return this;
    }

    public SignUpPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public SignUpPage clickNext() {
        click(nextButton);
        return this;
    }

    public SignUpPage fillAllFields(String firstName, String lastName, String email, String password) {
        return enterFirstName(firstName)
            .enterLastName(lastName)
            .enterUsername(email)
            .enterPassword(password);
    }
}
