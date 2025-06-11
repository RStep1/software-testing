package com.mycompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {

    @FindBy(xpath = "//*[@id=\"content\"]/div/section/div/h1")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div/div[1]/header/div/div[2]/div[1]/div/div/a[4]")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        super(driver);
        driver.get(AppUrls.HOME);
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public boolean isLogoutButtonVisible() {
        return isVisible(logoutButton);
    }
}
