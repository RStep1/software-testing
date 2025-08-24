package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mycompany.util.AppUrls;

public class HomePage extends BasePage {

    private By welcomeMessageLocator = By.xpath("/html/body/div[1]/div[2]/div/div/main/section[1]/div[1]/h1");
    private By goodbyHeaderLocator = By.xpath("//*[@id=\"javascript-content\"]/div/div[1]/main/div/p");

    private By logoutButtonLocator = By.xpath("//a[contains(@href, 'logout')]");


    private By profileImageButtonLocator = By.xpath("//button[contains(@class, 'me-menu-dropdown')]");
    private By jobTitleFieldLocator = By.xpath("//*[@id=\"keywords-input\"]");
    private By locationFieldLocator = By.xpath("//*[@id=\"location-input\"]");
    
    @FindBy(xpath = "//*[@id=\"content\"]/div/section/div/div/form/div[1]/div/button")
    private WebElement clearJobTitleFieldButton;

    @FindBy(xpath = "//*[@id=\"content\"]/div/section/div/div/form/div[2]/div/button")
    private WebElement clearLocationFieldButton;

    @FindBy(xpath = "//*[@id=\"search-button\"]")
    private WebElement searchJobButton;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/div[1]/h1")
    private WebElement jobsFoundHeader;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/div[1]/h1")
    private WebElement noResultsFoundHeader;

    @FindBy(xpath = "/html/body/div[3]/div/div/div/div/div/div[1]/div/button[3]")
    private WebElement topRatedFirstFilterButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage open(WebDriver driver) {
        driver.get(AppUrls.HOME);
        return new HomePage(driver);
    }

    public String getWelcomeMessage() {
        // return super.getWait().until(ExpectedConditions
        //     .visibilityOfElementLocated(welcomeMessageLocator))
        //     .getText();
        return getText(welcomeMessageLocator);
    }

    // public boolean isLogoutButtonVisible() {
    //     return isVisible(logoutButton);
    // }

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

    public HomePage clearFindJobFormFields() {
        return clickClearJobTitleFieldButton().clickClearLocationFieldButton();
    }

    public HomePage clickClearJobTitleFieldButton() {
        if (!isFieldEmpty(jobTitleFieldLocator)) {
            click(clearJobTitleFieldButton);
        }
        return this;
    }

    public HomePage clickClearLocationFieldButton() {
        if (!isFieldEmpty(locationFieldLocator))
        click(clearLocationFieldButton);
        return this;
    }

    public String getGoodbyMessage() {
        return getText(goodbyHeaderLocator);
    }

    public void searchForJob(String jobTitle, String location) {
        safeType(jobTitleFieldLocator, jobTitle);
        safeType(locationFieldLocator, location);
        click(searchJobButton);
        // waitForResultsRefresh();
    }

    public boolean areResultsDisplayed() {
        return isVisible(jobsFoundHeader);
    }

    public boolean isNoResultsMessageDisplayed() {
        return isVisible(noResultsFoundHeader);
    }

    public void applyTopRatedFilter() {
        click(topRatedFirstFilterButton);
        waitForResultsRefresh();
    }

    public String getFirstJobTitle() {
        By firstJobLocator = By.xpath("//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/ol/li[1]/a/article/div[2]/div[1]/div[1]/div/h2");
        return super.getWait().until(ExpectedConditions.visibilityOfElementLocated(firstJobLocator)).getText();
    }

    public boolean areLocationResultsDisplayed(String location) {
        By locationLocator = By.xpath("//*[contains(text(), '" + location + "')]");
        return !super.getDriver().findElements(locationLocator).isEmpty();
    }

    // public void clickProfileImage() {
    //     click(profileImageButton);
    // }

    public boolean isProfileDropdownDisplayed() {
        By dropdownLocator = By.xpath("//*[@id=\"profile-dropdown\"]");
        return isVisible(super.getDriver().findElement(dropdownLocator));
    }

    public int getResultsCount() {
        By resultsLocator = By.xpath("//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/ol/li");
        return super.getDriver().findElements(resultsLocator).size();
    }

    public void clickFirstJob() {
        By firstJobLocator = By.xpath("//*[@id=\"app\"]/div/div[2]/div/div[1]/div/main/ol/li[1]/a");
        click(super.getDriver().findElement(firstJobLocator));
    }

    public boolean isJobDetailsPageDisplayed(String jobTitle) {
        By jobTitleLocator = By.xpath("//h1[contains(text(), '" + jobTitle + "')]");
        return isVisible(super.getDriver().findElement(jobTitleLocator));
    }

    private void waitForResultsRefresh() {
        By loadingIndicator = By.xpath("//*[contains(@class, 'loading-indicator')]");
        super.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loadingIndicator));
    }
}
