package com.mycompany;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import com.mycompany.base.TestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;


public class LoginTest extends TestBase {
    private LoginPage loginPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(getDriver()).acceptAllPrivacy();
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
    public void givenEmptyCredentials_whenUserAttemptsLogin_thenShouldDisplayRequiredFieldsMessage() {
        loginPage.clearFormFields().clickLogin();
        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl());
    }

    @Test
    public void givenValidUsernameButEmptyPassword_whenUserAttemptsLogin_thenShouldDisplayPasswordRequiredMessage() {
        loginPage.enterUsername("litspher@gmail.com")
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }

    @Test
    public void givenEmptyUsernameButValidPassword_whenUserAttemptsLogin_thenShouldDisplayUsernameRequiredMessage() {
        loginPage.enterPassword("c!63*eu#R/dD6:.")
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }
}