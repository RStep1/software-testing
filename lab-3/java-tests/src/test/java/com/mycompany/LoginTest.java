package com.mycompany;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import com.mycompany.base.TestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;

@Disabled
public class LoginTest extends TestBase {
    private LoginPage loginPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
    }

    @Test
    public void givenValidCredentials_whenUserLogsIn_thenShouldNavigateToHomePage() {
        HomePage homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
        
        assertEquals("Find the right job for you.", homePage.getWelcomeMessage(), "Welcome message does not match");
    }

    @Test
    public void givenInvalidCredentials_whenUserLogsIn_thenShouldDisplayErrorMessage() {
        loginPage.enterUsername("invalidUser")
            .enterPassword("wrongPass")
            .clickLogin();

        assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        assertEquals("Your username or password is incorrect. Please try again.", 
                     loginPage.getLoginErrorMessage(), "Error message does not match");
    }

    @Test
    public void givenEmptyCredentials_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.clearFormFields().clickLogin();
        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl());
    }

    @Test
    public void givenValidUsernameButEmptyPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterUsername("litspher@gmail.com")
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }

    @Test
    public void givenEmptyUsernameButValidPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterPassword("c!63*eu#R/dD6:.")
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }
}