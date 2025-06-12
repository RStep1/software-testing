package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {

    private By welcomeMessageLocator = By.xpath("//*[@id=\"content\"]/div/section/div/h1");

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div/div[1]/header/div/div[2]/div[1]/div/div/a[4]")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage open(WebDriver driver) {
        driver.get(AppUrls.HOME);
        return new HomePage(driver);
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(welcomeMessageLocator))
            .getText();
    }

    public boolean isLogoutButtonVisible() {
        return isVisible(logoutButton);
    }
}
