package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchJobPage extends BasePage {
    private static final By queryInputMenuLocator = By.xpath("//ul[contains(@id, 'query-input-menu')]");
    private static final By jobTitleFieldLocator = By.xpath("//*[@id=\"keywords-input\"]");
    private static final By locationFieldLocator = By.xpath("//*[@id=\"location-input\"]");
    private static final By resultsHeaderLocator = By.xpath("//div[contains(@class, 'results-header')]");
    private static final By searchResultsListLocator = By.xpath("//ol/li[.//article[contains(@class, 'job-teaser-list-item')]]");
    private static final By firstResultLocator = By.xpath("(//ol/li[.//article[contains(@class, 'job-teaser-list-item')]])[1]");
    private static final By firstResultTitleLocator = By.xpath("(//ol/li[.//article[contains(@class, 'job-teaser-list-item')]])[1]//h2");
    private static final By firstResultLinkLocator = By.xpath("(//ol/li[.//article[contains(@class, 'job-teaser-list-item')]])[1]//a");
    
    private static final By clearJobTitleFieldButtonLocator = By.xpath("//*[@id='keywords-input']/following-sibling::button[@aria-label='Clear']");
    private static final By clearLocationFieldButtonLocator = By.xpath("//*[@id='location-input']/following-sibling::button[@aria-label='Clear']");
    private static final By searchJobButtonLocator = By.xpath("//button[@id='search-button']");
    
    private static final By topRatedFirstFilterButtonLocator = By.xpath("//button[.//span[text()='Top-rated first']]");

    public SearchJobPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public boolean isQueryInputMenuDisplayed() {
        return isElementLocatorVisible(queryInputMenuLocator);
    }

    public SearchJobPage clearFindJobFormFields() {
        return clickClearJobTitleFieldButton().clickClearLocationFieldButton();
    }

    public SearchJobPage clickClearJobTitleFieldButton() {
        if (!isFieldEmpty(jobTitleFieldLocator)) {
            click(getDriver().findElement(clearJobTitleFieldButtonLocator));
        }
        return this;
    }

    public SearchJobPage clickClearLocationFieldButton() {
        if (!isFieldEmpty(locationFieldLocator)) {
            click(getDriver().findElement(clearLocationFieldButtonLocator));
        }
        return this;
    }

    public void searchForJob(String jobTitle, String location) {
        safeType(jobTitleFieldLocator, jobTitle);
        safeType(locationFieldLocator, location);
        click(getDriver().findElement(searchJobButtonLocator));
        waitForResultsRefresh();
    }

    public boolean areResultsDisplayed() {
        return isElementLocatorVisible(resultsHeaderLocator);
    }

    public void applyTopRatedFilter() {
        click(getDriver().findElement(topRatedFirstFilterButtonLocator));
        waitForResultsRefresh();
    }

    public String getFirstJobTitle() {
        return getText(firstResultTitleLocator);
    }

    public boolean areLocationResultsDisplayed(String location) {
        By locationLocator = By.xpath("//*[contains(text(), '" + location + "')]");
        return !getDriver().findElements(locationLocator).isEmpty();
    }

    public int getResultsCount() {
        return getDriver().findElements(searchResultsListLocator).size();
    }

    public JobDetailsPage clickFirstJob() {
        String originalWindowHandle = getDriver().getWindowHandle();
        click(getDriver().findElement(firstResultLinkLocator));
        waitForNewWindowAndSwitch(originalWindowHandle);
        return new JobDetailsPage(getDriver());
    }

    public boolean isJobDetailsPageDisplayed(String jobTitle) {
        By jobTitleLocator = By.xpath("//h1[contains(text(), '" + jobTitle + "')]");
        return isElementLocatorVisible(jobTitleLocator);
    }

    public SearchJobPage closeJobDetailsAndReturn() {
        if (getDriver().getWindowHandles().size() > 1) {
            getDriver().close();
            for (String handle : getDriver().getWindowHandles()) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
        waitForPageToLoad();
        return this;
    }

    private void waitForResultsRefresh() {
        By loadingIndicator = By.xpath("//*[contains(@class, 'loading-indicator')]");
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(loadingIndicator));
    }

    public String getResultsHeaderText() {
        return getText(resultsHeaderLocator);
    }
}