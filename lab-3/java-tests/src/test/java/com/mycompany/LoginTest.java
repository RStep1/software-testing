package com.mycompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mycompany.base.LocalTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;

@Disabled
public class LoginTest extends LocalTestBase {
    private LoginPage loginPage;
    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
    }

    @Test
    public void givenEmptyCredentials_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.clearFormFields().clickLogin();
        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl());
    }

    @Test
    public void givenValidUsernameButEmptyPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterUsername(TEST_USERNAME)
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }

    @Test
    public void givenEmptyUsernameButValidPassword_whenUserAttemptsLogin_thenShouldStayOnLoginPage() {
        loginPage.enterPassword(TEST_PASSWORD)
            .clickLogin();

        assertEquals(AppUrls.LOGIN, getDriver().getCurrentUrl(), "Should stay on login page");
    }
}