package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {
    private static final By welcomeMessageLocator = By.xpath("/html/body/div[1]/div[2]/div/div/main/section[1]/div[1]/h1");
    private static final By goodbyHeaderLocator = By.xpath("//*[@id=\"javascript-content\"]/div/div[1]/main/div/p");
    private static final By logoutButtonLocator = By.xpath("//a[contains(@href, 'logout')]");
    private static final By profileImageButtonLocator = By.xpath("//button[contains(@class, 'me-menu-dropdown')]");
    private static final By inputBarLocator = By.xpath("//a[contains(@class, 'fake-input')]");
    private static final By searchButtonLocator = By.xpath("//div[contains(@class, 'search-dropdown')]");
    private static final By popOverSearchOptionsLocator = By.xpath("//div[contains(@class, 'pop-over')]");
    private static final By NETWORK_BUTTON_LOCATOR = By.xpath("//a[@data-testid='frame-vnav-members']");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage open(WebDriver driver) {
        driver.get(AppUrls.HOME);
        return new HomePage(driver);
    }

    public NetworkPage clickNetworkMemeberPage() {
        WebElement networkButton = getWait().until(ExpectedConditions.elementToBeClickable(NETWORK_BUTTON_LOCATOR));
        click(networkButton);
        return navigateToNetworkPage();
    }

    private NetworkPage navigateToNetworkPage() {
        return new NetworkPage(getDriver());
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessageLocator);
    }

    public HomePage clickProfileImageButton() {
        WebElement profileButton = getWait().until(ExpectedConditions.elementToBeClickable(profileImageButtonLocator));
        click(profileButton);
        
        getWait().until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/header/section/div/nav/div[2]/div")
        ));
        return this;
    }

    public HomePage clickLogoutButton() {
        WebElement logoutButton = getWait().until(ExpectedConditions.elementToBeClickable(logoutButtonLocator));
        click(logoutButton);
        return this;
    }
    
    public SearchJobPage clickInputBar() {
        WebElement inputBar = getWait().until(ExpectedConditions.elementToBeClickable(inputBarLocator));
        click(inputBar);
        return navigateToSearchPage();
    }
    
    public HomePage clickSearchButton() {
        WebElement searchButton = getWait().until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
        click(searchButton);
        return this;
    }

    private SearchJobPage navigateToSearchPage() {
        return new SearchJobPage(super.getDriver());
    }

    public String getGoodbyMessage() {
        return getText(goodbyHeaderLocator);
    }

    public boolean isPopOverSearchOptionsDisplayed() {
        return isElementLocatorVisible(popOverSearchOptionsLocator);
    }
}
