package com.mycompany;

import com.mycompany.base.LoggedInTestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// @Disabled
public class HomeTest extends LoggedInTestBase {
    private HomePage homePage;
    private final String TEST_JOB_TITLE = "Software Engineer";
    private final String TEST_LOCATION = "Berlin";
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        
        if (homePage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.HOME);
        
        homePage.clearFindJobFormFields();
        
        try {
            homePage.waitForPageToLoad();
        } catch (Exception e) {
            getDriver().navigate().refresh();
            homePage.waitForPageToLoad();
        }
    }

    @Disabled
    @Test
    public void givenUserSignedUp_whenClickOnLogOutButton_thenUserLogout() {
        homePage.clickProfileImageButton().clickLogoutButton();

        assertEquals(homePage.getGoodbyMessage(), "You're now logged out. Want to drop in again?");
    }

    @Test
    public void givenHomePage_whenSearchingWithValidJobTitle_thenResultsShouldBeDisplayed() {
        homePage.clearFindJobFormFields().searchForJob(TEST_JOB_TITLE, "");
        assertTrue(homePage.areResultsDisplayed());
    }

    @Test
    public void givenHomePage_whenSearchingWithInvalidCriteria_thenNoResultsMessageShouldAppear() {
        homePage.clearFindJobFormFields().searchForJob("InvalidJobTitleXYZ123", "");
        assertTrue(homePage.isNoResultsMessageDisplayed());
    }

    @Disabled
    @Test
    public void givenHomePage_whenApplyingTopRatedFilter_thenFirstResultShouldBeDifferent() {
        homePage.searchForJob(TEST_JOB_TITLE, "");

        String firstJobBeforeFilter = homePage.getFirstJobTitle();
        homePage.applyTopRatedFilter();
        String firstJobAfterFilter = homePage.getFirstJobTitle();

        assertFalse(firstJobBeforeFilter.equals(firstJobAfterFilter));
    }

    @Disabled //
    @Test
    public void givenHomePage_whenSearchingWithLocation_thenResultsShouldContainLocation() {
        homePage.searchForJob(TEST_JOB_TITLE, TEST_LOCATION);
        assertTrue(homePage.areLocationResultsDisplayed(TEST_LOCATION));
    }

    @Disabled
    @Test
    public void givenHomePage_whenClickingProfileImage_thenProfileDropdownShouldAppear() {
        homePage.clickProfileImage();
        assertTrue(homePage.isProfileDropdownDisplayed());
    }

    @Disabled
    @Test
    public void givenHomePage_whenChangingSearchCriteria_thenResultsShouldUpdate() {
        homePage.searchForJob("Java Developer", "");
        int initialResultsCount = homePage.getResultsCount();
        
        homePage.searchForJob("Python Developer", "");
        int updatedResultsCount = homePage.getResultsCount();
        
        assertNotEquals(initialResultsCount, updatedResultsCount);
    }

    @Disabled
    @Test
    public void givenHomePage_whenClickingOnFirstJob_thenJobDetailsPageShouldOpen() {
        String firstJobTitle = homePage.getFirstJobTitle();
        homePage.clickFirstJob();
        assertTrue(homePage.isJobDetailsPageDisplayed(firstJobTitle));
    }
}
