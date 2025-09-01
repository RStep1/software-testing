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

@Disabled
public class SearchJobTest extends LocalTestBase {
    private SearchJobPage searchJobPage;
    private static final String TEST_JOB_TITLE = "Software Engineer";
    private static final String TEST_LOCATION = "Berlin";
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
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
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.SEARCH_JOB);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }
    
    @Test
    public void givenSearchPage_whenSearchingWithValidJobTitle_thenResultsShouldBeDisplayed() {
        searchJobPage.clearFindJobFormFields().searchForJob(TEST_JOB_TITLE, "");
        assertTrue(searchJobPage.areResultsDisplayed());
    }

    @Test
    public void givenSearchPage_whenSearchingWithInvalidCriteria_thenNoResultsFound() {
        searchJobPage.clearFindJobFormFields().searchForJob("InvalidJobTitleXYZ123", "");

        assertEquals("No results found", searchJobPage.getResultsHeaderText());
    }

    @Test
    public void givenSearchPage_whenApplyingTopRatedFilter_thenFirstResultShouldBeDifferent() {
        searchJobPage.searchForJob(TEST_JOB_TITLE, "");

        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyTopRatedFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();

        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }

    @Test
    public void givenSearchPage_whenSearchingWithLocation_thenResultsShouldContainLocation() {
        searchJobPage.searchForJob(TEST_JOB_TITLE, TEST_LOCATION);
        assertTrue(searchJobPage.areLocationResultsDisplayed(TEST_LOCATION));
    }

    @Test
    public void givenSearchPage_whenChangingSearchCriteria_thenResultsShouldUpdate() {
        searchJobPage.searchForJob("Java Developer", "Munich");
        String firstJobFirstSearch = searchJobPage.getFirstJobTitle();
        
        searchJobPage.searchForJob("Python Developer", "Zurich");
        String firstJobSecondSearch = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobFirstSearch.equals(firstJobSecondSearch));
    }

    @Test
    public void givenSearchPage_whenClickingOnFirstJob_thenJobDetailsPageShouldOpen() {
        String firstJobTitle = searchJobPage.getFirstJobTitle();
        String originalWindowHandle = getDriver().getWindowHandle();

        JobDetailsPage jobDetailsPage = searchJobPage.clickFirstJob();

        assertEquals(firstJobTitle, jobDetailsPage.getJobTitle(), 
                     "Job title should match the clicked result");

        jobDetailsPage.closeAndReturnToPreviousTab(originalWindowHandle);

        assertTrue(searchJobPage.areResultsDisplayed(), 
                   "Should return to search results page");
    }

    @Test
    public void givenSearchPage_whenSearchingWithLocationOnly_thenResultsShouldBeDisplayed() {
        String location = "Munich";
        
        searchJobPage.searchForJob("", location);
        
        assertTrue(searchJobPage.areResultsDisplayed());
        assertTrue(searchJobPage.areLocationResultsDisplayed(location));
    }

    @Test
    public void givenSearchPage_whenApplyingPartTimeFilter_thenResultsShouldChange() {
        searchJobPage.searchForJob("Developer", "Berlin");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyPartTimeFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }

    @Test
    public void givenSearchPage_whenApplyingFullTimeFilter_thenResultsShouldBeRelevant() {
        searchJobPage.searchForJob("Engineer", "Hamburg");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyFullTimeFilter();
        searchJobPage.applyProfessionalExperiencedFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }

    @Test
    public void givenSearchPage_whenApplyingStudentInternFilter_thenShouldShowEntryLevelPositions() {
        searchJobPage.searchForJob("IT", "Cologne");
        
        String firstJobBeforeFilter = searchJobPage.getFirstJobTitle();
        searchJobPage.applyStudentInternFilter();
        String firstJobAfterFilter = searchJobPage.getFirstJobTitle();
        
        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }
}
