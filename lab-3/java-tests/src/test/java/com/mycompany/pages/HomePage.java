package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {
    private static final By WELCOME_MESSAGE_LOCATOR = By.xpath("/html/body/div[1]/div[2]/div/div/main/section[1]/div[1]/h1");
    private static final By GOODBYE_HEADER_LOCATOR = By.xpath("//*[@id=\"javascript-content\"]/div/div[1]/main/div/p");
    private static final By LOGOUT_BUTTON_LOCATOR = By.xpath("//a[contains(@href, 'logout')]");
    private static final By PROFILE_IMAGE_BUTTON_LOCATOR = By.xpath("//button[contains(@class, 'me-menu-dropdown')]");
    private static final By INPUT_BAR_LOCATOR = By.xpath("//a[contains(@class, 'fake-input')]");
    private static final By SEARCH_BUTTON_LOCATOR = By.xpath("//div[contains(@class, 'search-dropdown')]");
    private static final By POPOVER_SEARCH_OPTIONS_LOCATOR = By.xpath("//div[contains(@class, 'pop-over')]");
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
        return getText(WELCOME_MESSAGE_LOCATOR);
    }

    public HomePage clickProfileImageButton() {
        WebElement profileButton = getWait().until(ExpectedConditions.elementToBeClickable(PROFILE_IMAGE_BUTTON_LOCATOR));
        click(profileButton);
        
        getWait().until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/header/section/div/nav/div[2]/div")
        ));
        return this;
    }

    public HomePage clickLogoutButton() {
        WebElement logoutButton = getWait().until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON_LOCATOR));
        click(logoutButton);
        return this;
    }
    
    public SearchJobPage clickInputBar() {
        WebElement inputBar = getWait().until(ExpectedConditions.elementToBeClickable(INPUT_BAR_LOCATOR));
        click(inputBar);
        return navigateToSearchPage();
    }
    
    public HomePage clickSearchButton() {
        WebElement searchButton = getWait().until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON_LOCATOR));
        click(searchButton);
        return this;
    }

    private SearchJobPage navigateToSearchPage() {
        return new SearchJobPage(super.getDriver());
    }

    public String getGoodbyMessage() {
        return getText(GOODBYE_HEADER_LOCATOR);
    }

    public boolean isPopOverSearchOptionsDisplayed() {
        return isElementLocatorVisible(POPOVER_SEARCH_OPTIONS_LOCATOR);
    }
}
