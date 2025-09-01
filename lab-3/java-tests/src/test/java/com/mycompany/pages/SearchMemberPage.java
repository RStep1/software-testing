package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchMemberPage extends BasePage {
    private static final By MEMBERS_COUNT_HEADLINE_LOCATOR = 
        By.xpath("//h2[@data-xds='Headline' and contains(text(), 'members found')]");
    private static final By NEWEST_FILTER_LOCATOR = 
        By.xpath("//button[.//span[contains(text(), 'Newest first')]]");
    private static final By DIRECTOR_CONTACT_FILTER_LOCATOR = 
        By.xpath("//button[.//span[contains(text(), 'Direct contacts')]]");
    private static final By SEARCH_RESULTS_LIST_LOCATOR = 
        By.xpath("//ol[@data-testid='search-list']");
    private static final By RESULT_FIRST_MEMBER_LOCATOR = 
        By.xpath("(//ol[@data-testid='search-list']//li[.//div[@data-testid='members-search-result-item']])[1]");
    private static final By RESULT_FIRST_MEMBER_NAME_LOCATOR = 
        By.xpath("(//ol[@data-testid='search-list']//li[.//div[@data-testid='members-search-result-item']]//h2[@data-xds='Headline'])[1]");
    private static final By RESULT_FIRST_MEMBER_LOCATION_LOCATOR = 
        By.xpath("(//ol[@data-testid='search-list']//li[.//div[@data-testid='members-search-result-item']]//p[contains(text(), 'Germany') or contains(text(), 'location')])[1]");
    private static final By GO_TO_HOMEPAGE_BUTTON_LOCATOR = 
        By.xpath("//a[contains(@title, 'Go to XING homepage')]");

    public SearchMemberPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public HomePage clickGoToHomepage() {
        WebElement button = getWait().until(ExpectedConditions.visibilityOfElementLocated(GO_TO_HOMEPAGE_BUTTON_LOCATOR));
        click(button);
        HomePage homePage = new HomePage(getDriver());
        waitForPageToLoad();
        return homePage;
    }

    public boolean isMembersCountDisplayed() {
        return isElementLocatorVisible(MEMBERS_COUNT_HEADLINE_LOCATOR);
    }
    
    public boolean containsMembersFoundText() {
        try {
            String text = getText(MEMBERS_COUNT_HEADLINE_LOCATOR);
            return text.contains("members found");
        } catch (Exception e) {
            return false;
        }
    }

    public String getMemberFoundText() {
        return getText(MEMBERS_COUNT_HEADLINE_LOCATOR);
    }
    
    public boolean isNewestFilterDisplayed() {
        return isElementLocatorVisible(NEWEST_FILTER_LOCATOR);
    }
    
    public boolean isDirectContactsFilterDisplayed() {
        return isElementLocatorVisible(DIRECTOR_CONTACT_FILTER_LOCATOR);
    }
    
    public void clickNewestFilter() {
        WebElement filter = getWait().until(ExpectedConditions.visibilityOfElementLocated(NEWEST_FILTER_LOCATOR));
        click(filter);
    }
    
    public void clickDirectContactsFilter() {
        WebElement filter = getWait().until(ExpectedConditions.visibilityOfElementLocated(DIRECTOR_CONTACT_FILTER_LOCATOR));
        click(filter);
    }
    
    public boolean areSearchResultsDisplayed() {
        return isElementLocatorVisible(SEARCH_RESULTS_LIST_LOCATOR);
    }
    
    
    public String getFirstMemberName() {
        return getText(RESULT_FIRST_MEMBER_NAME_LOCATOR);
    }
    
    public String getFirstMemberLocation() {
        return getText(RESULT_FIRST_MEMBER_LOCATION_LOCATOR);
    }
    
    public boolean isNewestFilterActive() {
        WebElement filter = getWait().until(ExpectedConditions.visibilityOfElementLocated(NEWEST_FILTER_LOCATOR));
        return "true".equals(filter.getAttribute("aria-pressed"));
    }
    
    public boolean isDirectContactsFilterActive() {
        WebElement filter = getWait().until(ExpectedConditions.visibilityOfElementLocated(DIRECTOR_CONTACT_FILTER_LOCATOR));
        return "true".equals(filter.getAttribute("aria-pressed"));
    }
}