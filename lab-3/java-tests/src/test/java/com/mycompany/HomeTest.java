package com.mycompany;

import com.mycompany.base.TestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.pages.SearchPage;
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

@Disabled
public class HomeTest extends TestBase {
    private HomePage homePage;
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        
        if (homePage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("jobs"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.HOME);
        
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    @Test
    public void givenUserSignedUp_whenClickOnLogOutButton_thenUserLogout() {
        homePage.clickProfileImageButton().clickLogoutButton();

        assertEquals(homePage.getGoodbyMessage(), "You're now logged out. Want to drop in again?");
    }

    @Test
    public void givenUesrSignedUp_whenClickOnSearchButton_thenSearchPageOpens() {
        homePage.clickSearchButton();

        assertTrue(homePage.isPopOverSearchOptionsDisplayed());
    }

    @Test
    public void givenUesrSignedUp_whenClickOnInputBar_thenSearchPageOpens() {
        SearchPage searchPage = homePage.clickInputBar();

        assertTrue(searchPage.isQueryInputMenuDisplayed());
    }
}
