package com.mycompany;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mycompany.base.GridTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Ignore
@Epic("Grid Testing")
@Feature("Grid Login Functionality")
@Listeners({AllureTestNg.class})
public class GridLoginTest extends GridTestBase {
    private LoginPage loginPage;
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();

    @Parameters({"browser"})
    @BeforeMethod
    @Override
    @Step("Setup Grid environment for browser: {browser}")
    public void setUp(String browser) throws MalformedURLException {
        super.setUp(browser);
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
        attachGridEnvironmentInfo(browser);
    }

    @Test
    @Description("Test successful login with valid credentials in Grid environment")
    public void givenValidCredentials_whenUserLogsIn_thenShouldNavigateToHomePage() {
        HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
        
        assertEquals("Tell the XING AI about the job you want",
            homePage.getWelcomeMessage(), "Welcome message does not match");
        takeScreenshot("grid-valid-login-success");
        allureAddLog("Successful login in Grid environment");
    }

    @Test
    @Description("Test login with invalid credentials in Grid environment")
    public void givenInvalidCredentials_whenUserLogsIn_thenShouldDisplayErrorMessage() {
        loginPage.enterUsername("invalidUser")
            .enterPassword("wrongPass")
            .clickLogin();

        assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        assertEquals("Your username or password is incorrect. Please try again.", 
                     loginPage.getLoginErrorMessage(), "Error message does not match");
        takeScreenshot("grid-invalid-login-error");
        allureAddLog("Invalid login attempt in Grid environment - error message displayed");
    }

    @Step("Add Grid environment information")
    private void attachGridEnvironmentInfo(String browser) {
        RemoteWebDriver remoteDriver = (RemoteWebDriver) getDriver();

        allureAddEnvironmentInfo("Grid Browser", browser);
        allureAddEnvironmentInfo("Grid URL", getGridHubUrl());
        allureAddEnvironmentInfo("Remote Session ID", remoteDriver.getSessionId().toString());
        allureAddEnvironmentInfo("Execution Mode", "Selenium Grid");
        allureAddEnvironmentInfo("Target Platform", "Remote");
    }
}