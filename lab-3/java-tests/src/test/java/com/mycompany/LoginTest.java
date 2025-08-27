package com.mycompany;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;

import com.mycompany.base.GridTestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;

@Ignore
public class LoginTest extends GridTestBase {
    private LoginPage loginPage;

    @Parameters({"browser"})
    @BeforeMethod
    @Override
    public void setUp(String browser) throws MalformedURLException {
        super.setUp(browser);
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
    }

    @Test
    public void givenValidCredentials_whenUserLogsIn_thenShouldNavigateToHomePage() {
        HomePage homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
        
        assertEquals("Tell the XING AI about the job you want", homePage.getWelcomeMessage(), "Welcome message does not match");
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