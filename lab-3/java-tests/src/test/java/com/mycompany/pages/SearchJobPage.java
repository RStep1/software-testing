package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchJobPage extends BasePage {
    private static final By QUERY_INPUT_MENU_LOCATOR = By.xpath("//ul[contains(@id, 'query-input-menu')]");
    private static final By JOB_TITLE_FIELD_LOCATOR = By.xpath("//*[@id=\"keywords-input\"]");
    private static final By LOCATION_FIELD_LOCATOR = By.xpath("//*[@id=\"location-input\"]");
    private static final By RESULTS_HEADER_LOCATOR = By.xpath("//div[contains(@class, 'results-header')]");
    private static final By SEARCH_RESULTS_LIST_LOCATOR = By.xpath("//ol/li[.//article[contains(@class, 'job-teaser-list-item')]]");
    private static final By FIRST_RESULT_TITLE_LOCATOR = By.xpath("(//ol/li[.//article[contains(@class, 'job-teaser-list-item')]])[1]//h2");
    private static final By FIRST_RESULT_LINK_LOCATOR = By.xpath("(//ol/li[.//article[contains(@class, 'job-teaser-list-item')]])[1]//a");
    
    private static final By CLEAR_JOB_TITLE_FIELD_BUTTON_LOCATOR = By.xpath("//*[@id='keywords-input']/following-sibling::button[@aria-label='Clear']");
    private static final By CLEAR_LOCATION_FIELD_BUTTON_LOCATOR = By.xpath("//*[@id='location-input']/following-sibling::button[@aria-label='Clear']");
    private static final By SEARCH_JOB_BUTTON_LOCATOR = By.xpath("//button[@id='search-button']");
    
    private static final By TOP_RATED_FIRST_FILTER_BUTTON_LOCATOR = By.xpath("//button[.//span[text()='Top-rated first']]");
    private static final By PART_TIME_FILTER_LOCATOR = By.xpath("//button[.//span[text()='Part-time']]");
    private static final By FULL_TIME_FILTER_LOCATOR = By.xpath("//button[.//span[text()='Full-time']]");
    private static final By STUDENT_INTERN_FILTER_LOCATOR = By.xpath("//button[.//span[text()='Student/Intern']]");
    private static final By ENTRY_LEVEL_FILTER_LOCATOR = By.xpath("//button[.//span[text()='Entry Level']]");
    private static final By PROFESSIONAL_EXPERIENCED_FILTER_LOCATOR = By.xpath("//button[.//span[text()='Professional/Experienced']]");

    public SearchJobPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public boolean isQueryInputMenuDisplayed() {
        return isElementLocatorVisible(QUERY_INPUT_MENU_LOCATOR);
    }

    public SearchJobPage clearFindJobFormFields() {
        return clickClearJobTitleFieldButton().clickClearLocationFieldButton();
    }

    public SearchJobPage clickClearJobTitleFieldButton() {
        if (!isFieldEmpty(JOB_TITLE_FIELD_LOCATOR)) {
            click(getDriver().findElement(CLEAR_JOB_TITLE_FIELD_BUTTON_LOCATOR));
        }
        return this;
    }

    public SearchJobPage clickClearLocationFieldButton() {
        if (!isFieldEmpty(LOCATION_FIELD_LOCATOR)) {
            click(getDriver().findElement(CLEAR_LOCATION_FIELD_BUTTON_LOCATOR));
        }
        return this;
    }

    public void searchForJob(String jobTitle, String location) {
        safeType(JOB_TITLE_FIELD_LOCATOR, jobTitle);
        safeType(LOCATION_FIELD_LOCATOR, location);
        click(getDriver().findElement(SEARCH_JOB_BUTTON_LOCATOR));
        waitForResultsRefresh();
    }

    public boolean areResultsDisplayed() {
        return isElementLocatorVisible(RESULTS_HEADER_LOCATOR);
    }

    public void applyTopRatedFilter() {
        click(getDriver().findElement(TOP_RATED_FIRST_FILTER_BUTTON_LOCATOR));
        waitForResultsRefresh();
    }

    public void applyPartTimeFilter() {
        click(getDriver().findElement(PART_TIME_FILTER_LOCATOR));
        waitForResultsRefresh();
    }
    
    public void applyFullTimeFilter() {
        click(getDriver().findElement(FULL_TIME_FILTER_LOCATOR));
        waitForResultsRefresh();
    }
    
    public void applyStudentInternFilter() {
        click(getDriver().findElement(STUDENT_INTERN_FILTER_LOCATOR));
        waitForResultsRefresh();
    }
    
    public void applyEntryLevelFilter() {
        click(getDriver().findElement(ENTRY_LEVEL_FILTER_LOCATOR));
        waitForResultsRefresh();
    }

    public void applyProfessionalExperiencedFilter() {
        click(getDriver().findElement(PROFESSIONAL_EXPERIENCED_FILTER_LOCATOR));
        waitForResultsRefresh();
    }

    public String getFirstJobTitle() {
        return getText(FIRST_RESULT_TITLE_LOCATOR);
    }

    public boolean areLocationResultsDisplayed(String location) {
        By locationLocator = By.xpath("//*[contains(text(), '" + location + "')]");
        return !getDriver().findElements(locationLocator).isEmpty();
    }

    public int getResultsCount() {
        return getDriver().findElements(SEARCH_RESULTS_LIST_LOCATOR).size();
    }

    public JobDetailsPage clickFirstJob() {
        String originalWindowHandle = getDriver().getWindowHandle();
        click(getDriver().findElement(FIRST_RESULT_LINK_LOCATOR));
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
        return getText(RESULTS_HEADER_LOCATOR);
    }
}