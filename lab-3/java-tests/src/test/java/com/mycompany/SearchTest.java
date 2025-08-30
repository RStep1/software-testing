package com.mycompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mycompany.base.LocalTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.JobDetailsPage;
import com.mycompany.pages.LoginPage;
import com.mycompany.pages.SearchPage;
import com.mycompany.util.AppUrls;

@Disabled
public class SearchTest extends LocalTestBase {
    private SearchPage searchPage;
    private final String TEST_JOB_TITLE = "Software Engineer";
    private final String TEST_LOCATION = "Berlin";
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        
        if (searchPage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
            searchPage = homePage.clickInputBar();
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("search"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.SEARCH);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }
    
    @Test
    public void givenSearchPage_whenSearchingWithValidJobTitle_thenResultsShouldBeDisplayed() {
        searchPage.clearFindJobFormFields().searchForJob(TEST_JOB_TITLE, "");
        assertTrue(searchPage.areResultsDisplayed());
    }

    @Test
    public void givenSearchPage_whenSearchingWithInvalidCriteria_thenNoResultsFound() {
        searchPage.clearFindJobFormFields().searchForJob("InvalidJobTitleXYZ123", "");

        assertEquals("No results found", searchPage.getResultsHeaderText());
    }

    @Test
    public void givenSearchPage_whenApplyingTopRatedFilter_thenFirstResultShouldBeDifferent() {
        searchPage.searchForJob(TEST_JOB_TITLE, "");

        String firstJobBeforeFilter = searchPage.getFirstJobTitle();
        searchPage.applyTopRatedFilter();
        String firstJobAfterFilter = searchPage.getFirstJobTitle();

        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }

    @Test
    public void givenSearchPage_whenSearchingWithLocation_thenResultsShouldContainLocation() {
        searchPage.searchForJob(TEST_JOB_TITLE, TEST_LOCATION);
        assertTrue(searchPage.areLocationResultsDisplayed(TEST_LOCATION));
    }

    @Test
    public void givenSearchPage_whenChangingSearchCriteria_thenResultsShouldUpdate() {
        searchPage.searchForJob("Java Developer", "Munich");
        String firstJobFirstSearch = searchPage.getFirstJobTitle();
        
        searchPage.searchForJob("Python Developer", "Zurich");
        String firstJobSecondSearch = searchPage.getFirstJobTitle();
        
        assertFalse(firstJobFirstSearch.equals(firstJobSecondSearch));
    }

    @Test
    public void givenSearchPage_whenClickingOnFirstJob_thenJobDetailsPageShouldOpen() {
        String firstJobTitle = searchPage.getFirstJobTitle();
        String originalWindowHandle = getDriver().getWindowHandle();

        JobDetailsPage jobDetailsPage = searchPage.clickFirstJob();

        assertEquals(firstJobTitle, jobDetailsPage.getJobTitle(), 
                     "Job title should match the clicked result");

        jobDetailsPage.closeAndReturnToPreviousTab(originalWindowHandle);

        assertTrue(searchPage.areResultsDisplayed(), 
                   "Should return to search results page");
    }
}
