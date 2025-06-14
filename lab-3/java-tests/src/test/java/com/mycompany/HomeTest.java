package com.mycompany;

import com.mycompany.base.TestBase;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HomeTest extends TestBase {
    private HomePage homePage;
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
        homePage = loginPage.loginWithValidCredentials("litspher@gmail.com", "c!63*eu#R/dD6:.");
    }

    @Test
    public void givenUserSignedUp_whenClickOnLogOutButton_thenUserLogout() {
        homePage.clickProfileImageButton().clickLogoutButton();

        assertEquals(homePage.getGoodbyMessage(), "You're now logged out. Want to drop in again?");
    }
}
