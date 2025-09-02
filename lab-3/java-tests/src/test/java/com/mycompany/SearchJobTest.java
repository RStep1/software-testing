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
import com.mycompany.pages.SearchJobPage;
import com.mycompany.util.AppUrls;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

// @Disabled
@ExtendWith(AllureJunit5.class)
@Epic("Job Search")
@Feature("Job Search Functionality")
public class SearchJobTest extends LocalTestBase {
    private SearchJobPage searchJobPage;
    private static final String TEST_JOB_TITLE = "Software Engineer";
    private static final String TEST_LOCATION = "Berlin";
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    @Step("Setup test environment and login")
    public void setUp() {
        super.setUp();
        
        if (searchJobPage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
            searchJobPage = homePage.clickInputBar();
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("search"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                takeScreenshot("login-failed-search-job");
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
        allureAddEnvironmentInfo("Test Job Title", TEST_JOB_TITLE);
        allureAddEnvironmentInfo("Test Location", TEST_LOCATION);
    }

    @Step("Reset application state")
    private void resetApplicationState() {
        getDriver().get(AppUrls.SEARCH_JOB);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }
    
    @Test
    @Description("Test job search with valid job title")
    public void givenSearchPage_whenSearchingWithValidJobTitle_thenResultsShouldBeDisplayed() {
        searchJobPage.clearFindJobFormFields().searchForJob(TEST_JOB_TITLE, "");
        assertTrue(searchJobPage.areResultsDisplayed());
        takeScreenshot("valid-job-title-search");
        allureAddLog("Search completed for job title: " + TEST_JOB_TITLE);
    }

    @Test
    @Description("Test job search with invalid criteria")
    public void givenSearchPage_whenSearchingWithInvalidCriteria_thenNoResultsFound() {
        searchJobPage.clearFindJobFormFields().searchForJob("InvalidJobTitleXYZ123", "");

        assertEquals("No results found", searchJobPage.getResultsHeaderText());
        takeScreenshot("invalid-criteria-search");
        allureAddLog("No results found for invalid search criteria");
    }

    @Test
    @Description("Test top rated filter functionality")
    public void givenSearchPage_whenApplyingTopRatedFilter_thenFirstResultShouldBeDifferent() {
        searchJobPage.searchForJob(TEST_JOB_TITLE, "");

        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyTopRatedFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();

        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
        takeScreenshot("top-rated-filter-applied");
        allureAddLog("Top rated filter applied successfully");
    }

    @Test
    @Description("Test job search with location filter")
    public void givenSearchPage_whenSearchingWithLocation_thenResultsShouldContainLocation() {
        searchJobPage.searchForJob(TEST_JOB_TITLE, TEST_LOCATION);
        assertTrue(searchJobPage.areLocationResultsDisplayed(TEST_LOCATION));
        takeScreenshot("location-based-search");
        allureAddLog("Location-based search completed for: " + TEST_LOCATION);
    }

    @Test
    @Description("Test search criteria change functionality")
    public void givenSearchPage_whenChangingSearchCriteria_thenResultsShouldUpdate() {
        searchJobPage.searchForJob("Java Developer", "Munich");
        String firstJobFirstSearch = searchJobPage.getFirstJobTitle();
        
        searchJobPage.searchForJob("Python Developer", "Zurich");
        String firstJobSecondSearch = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobFirstSearch.equals(firstJobSecondSearch));
        takeScreenshot("search-criteria-change");
        allureAddLog("Search criteria changed successfully");
    }

    @Test
    @Description("Test job details page navigation")
    public void givenSearchPage_whenClickingOnFirstJob_thenJobDetailsPageShouldOpen() {
        String firstJobTitle = searchJobPage.getFirstJobTitle();
        String originalWindowHandle = getDriver().getWindowHandle();

        JobDetailsPage jobDetailsPage = searchJobPage.clickFirstJob();

        assertEquals(firstJobTitle, jobDetailsPage.getJobTitle(), 
                     "Job title should match the clicked result");

        jobDetailsPage.closeAndReturnToPreviousTab(originalWindowHandle);

        assertTrue(searchJobPage.areResultsDisplayed(), 
                   "Should return to search results page");
        takeScreenshot("job-details-navigation");
        allureAddLog("Navigated to job details and returned successfully");
    }

    @Test
    @Description("Test location-only search functionality")
    public void givenSearchPage_whenSearchingWithLocationOnly_thenResultsShouldBeDisplayed() {
        String location = "Munich";
        
        searchJobPage.searchForJob("", location);
        
        assertTrue(searchJobPage.areResultsDisplayed());
        assertTrue(searchJobPage.areLocationResultsDisplayed(location));
        takeScreenshot("location-only-search");
        allureAddLog("Location-only search completed for: " + location);
    }

    @Test
    @Description("Test part-time filter functionality")
    public void givenSearchPage_whenApplyingPartTimeFilter_thenResultsShouldChange() {
        searchJobPage.searchForJob("Developer", "Berlin");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyPartTimeFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
        takeScreenshot("part-time-filter");
        allureAddLog("Part-time filter applied successfully");
    }

    @Test
    @Description("Test full-time and professional experience filters")
    public void givenSearchPage_whenApplyingFullTimeFilter_thenResultsShouldBeRelevant() {
        searchJobPage.searchForJob("Engineer", "Hamburg");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyFullTimeFilter();
        searchJobPage.applyProfessionalExperiencedFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
        takeScreenshot("full-time-experienced-filters");
        allureAddLog("Full-time and professional experience filters applied");
    }

    @Test
    @Description("Test student/intern filter functionality")
    public void givenSearchPage_whenApplyingStudentInternFilter_thenShouldShowEntryLevelPositions() {
        searchJobPage.searchForJob("IT", "Cologne");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyStudentInternFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
        takeScreenshot("student-intern-filter");
        allureAddLog("Student/Intern filter applied successfully");
    }
}