package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {

    private By welcomeMessageLocator = By.xpath("//*[@id=\"content\"]/div/section/div/h1");

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div/div[1]/header/section/div/nav/div[2]/div/div[5]/a")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div/div[1]/header/section/div/nav/div[2]/button/div/span/div/div[1]/div/img")
    private WebElement profileIgameButton;

    @FindBy(xpath = "//*[@id=\"keywords-input\"]")
    private WebElement jobTitleField;

    @FindBy(xpath = "//*[@id=\"location-input\"]")
    private WebElement locationField;

    @FindBy(xpath = "//*[@id=\"search-button\"]/div/span[1]/svg")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/div[1]/h1/text()[2]")
    private WebElement jobsFoundHeader;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/div[1]/h1")
    private WebElement noResultsFoundHeader;

    @FindBy(xpath = "/html/body/div[3]/div/div/div/div/div/div[1]/div/button[3]")
    private WebElement topRatedFirstFilterButton;

    @FindBy(xpath = "//*[@id=\"javascript-content\"]/div/div[1]/main/div/p")
    private WebElement goodbyHeader;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage open(WebDriver driver) {
        driver.get(AppUrls.HOME);
        return new HomePage(driver);
    }

    public String getWelcomeMessage() {
        return super.getWait().until(ExpectedConditions
            .visibilityOfElementLocated(welcomeMessageLocator))
            .getText();
    }

    public boolean isLogoutButtonVisible() {
        return isVisible(logoutButton);
    }

    public HomePage clickProfileImageButton() {
        click(profileIgameButton);
        return this;
    }

    public HomePage clickLogoutButton() {
        click(logoutButton);
        return this;
    }

    public String getGoodbyMessage() {
        return getText(goodbyHeader);
    }
}
