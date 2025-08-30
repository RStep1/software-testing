package com.mycompany;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mycompany.base.GridTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;

@Ignore
public class GridLoginTest extends GridTestBase {
    private LoginPage loginPage;
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();

    @Parameters({"browser"})
    @BeforeMethod
    @Override
    public void setUp(String browser) throws MalformedURLException {
        super.setUp(browser);
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
    }

    @Test
    public void givenValidCredentials_whenUserLogsIn_thenShouldNavigateToHomePage() {
        HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
        
        assertEquals("Tell the XING AI about the job you want",
            homePage.getWelcomeMessage(), "Welcome message does not match");
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
}
