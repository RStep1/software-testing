package com.mycompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mycompany.base.LocalTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

@Disabled
@ExtendWith(AllureJunit5.class)
@Epic("Authentication")
@Feature("Login Functionality")
public class LoginTest extends LocalTestBase {
    private LoginPage loginPage;
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();

    @BeforeEach
    @Override
    @Step("Setup test environment and navigate to login page")
    public void setUp() {
        super.setUp();
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
        attachEnvironmentInfo();
    }

    @Test
    @Description("Test login with empty credentials")
    public void givenEmptyCredentials_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.clearFormFields().clickLogin();
        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl());
        takeScreenshot("empty-credentials-login");
        allureAddLog("Login attempt with empty credentials - stayed on login page");
    }

    @Test
    @Description("Test login with valid username but empty password")
    public void givenValidUsernameButEmptyPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterUsername(TEST_USERNAME)
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
        takeScreenshot("valid-username-empty-password");
        allureAddLog("Login attempt with username only - stayed on login page");
    }

    @Test
    @Description("Test login with empty username but valid password")
    public void givenEmptyUsernameButValidPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterPassword(TEST_PASSWORD)
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
        takeScreenshot("empty-username-valid-password");
        allureAddLog("Login attempt with password only - stayed on login page");
    }

    @Step("Add environment information to report")
    private void attachEnvironmentInfo() {
        ChromeDriver chromeDriver = (ChromeDriver) getDriver();
        allureAddEnvironmentInfo("Browser", chromeDriver.getCapabilities().getBrowserName());
        allureAddEnvironmentInfo("Browser Version", chromeDriver.getCapabilities().getBrowserVersion());
        allureAddEnvironmentInfo("Login URL", AppUrls.LOGIN);
        allureAddEnvironmentInfo("Test Username", TEST_USERNAME);
    }
}