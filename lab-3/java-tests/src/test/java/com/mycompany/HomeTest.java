package com.mycompany;

import com.mycompany.base.LocalTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.pages.SearchJobPage;
import com.mycompany.util.AppUrls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

@Disabled
@ExtendWith(AllureJunit5.class)
@Epic("Home Page")
@Feature("Home Page Functionality")
public class HomeTest extends LocalTestBase {
    private HomePage homePage;
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    @Step("Setup test environment and perform user login")
    public void setUp() {
        super.setUp();
        
        if (homePage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("jobs"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                takeScreenshot("login-failed-homepage");
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
        attachHomePageEnvironmentInfo();
    }

    @Step("Reset application state")
    private void resetApplicationState() {
        getDriver().get(AppUrls.HOME);
        
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    @Test
    @Description("Test user logout functionality")
    public void givenUserSignedUp_whenClickOnLogOutButton_thenUserLogout() {
        homePage.clickProfileImageButton().clickLogoutButton();

        assertEquals(homePage.getGoodbyMessage(), "You're now logged out. Want to drop in again?");
        takeScreenshot("user-logout");
        allureAddLog("User successfully logged out");
    }

    @Test
    @Description("Test search button functionality")
    public void givenUesrSignedUp_whenClickOnSearchButton_thenSearchPageOpens() {
        homePage.clickSearchButton();

        assertTrue(homePage.isPopOverSearchOptionsDisplayed());
        takeScreenshot("search-button-click");
        allureAddLog("Search button clicked - popover options displayed");
    }

    @Test
    @Description("Test input bar functionality")
    public void givenUesrSignedUp_whenClickOnInputBar_thenSearchPageOpens() {
        SearchJobPage searchPage = homePage.clickInputBar();

        assertTrue(searchPage.isQueryInputMenuDisplayed());
        takeScreenshot("input-bar-click");
        allureAddLog("Input bar clicked - search page opened");
    }

    @Step("Add home page environment information")
    private void attachHomePageEnvironmentInfo() {
        allureAddEnvironmentInfo("Home URL", AppUrls.HOME);
        allureAddEnvironmentInfo("User Status", "Logged In");
        allureAddEnvironmentInfo("Welcome Message", homePage.getWelcomeMessage());
    }
}