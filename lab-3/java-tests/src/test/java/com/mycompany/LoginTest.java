package com.mycompany;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Disabled;

import com.mycompany.base.TestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.util.AppUrls;


public class LoginTest extends TestBase {
    
    @Test
    public void givenValidCredentials_whenUserLogsIn_thenShouldNavigateToHomePage() {
        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.acceptAllPrivacy();
        
        HomePage homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
        
        assertEquals(homePage.getWelcomeMessage(), "Find the right job for you.");
    }

    @Test
    public void givenInvalidCredentials_whenUserLogsIn_thenShouldDisplayErrorMessage() {
        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.acceptAllPrivacy();
         
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("wrongPass");
        loginPage.clickLogin();

        String errorMessage = loginPage.getLoginErrorMessage();
        assertEquals(errorMessage, "Your username or password is incorrect. Please try again.");
        assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Test
    public void givenEmptyCredentials_whenUserAttemptsLogin_thenShouldDisplayRequiredFieldsMessage() {
        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.acceptAllPrivacy();
        
        loginPage.clearFormFields();
        loginPage.clickLogin();
        
        String currentUrl = this.getDriver().getCurrentUrl();
        assertEquals(AppUrls.LOGIN, currentUrl);
    }

    @Test
    public void givenValidUsernameButEmptyPassword_whenUserAttemptsLogin_thenShouldDisplayPasswordRequiredMessage() {
        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.acceptAllPrivacy();
        
        loginPage.enterUsername("litspher@gmail.com");
        loginPage.clickLogin();
        
        String currentUrl = this.getDriver().getCurrentUrl();
        assertEquals(AppUrls.LOGIN, currentUrl);
    }
    
    @Test
    public void givenEmptyUsernameButValidPassword_whenUserAttemptsLogin_thenShouldDisplayUsernameRequiredMessage() {
        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.acceptAllPrivacy();
        
        loginPage.enterPassword("c!63*eu#R/dD6:.");
        loginPage.clickLogin();
        
        String currentUrl = this.getDriver().getCurrentUrl();
        assertEquals(AppUrls.LOGIN, currentUrl);
    }
}