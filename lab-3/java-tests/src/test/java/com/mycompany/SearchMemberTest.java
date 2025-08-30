package com.mycompany;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mycompany.base.LocalTestBase;
import com.mycompany.config.TestConfig;
import com.mycompany.pages.HomePage;
import com.mycompany.pages.LoginPage;
import com.mycompany.pages.NetworkPage;
import com.mycompany.pages.SearchMemberPage;
import com.mycompany.util.AppUrls;

public class SearchMemberTest extends LocalTestBase {
    private SearchMemberPage searchMemberPage;

    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        
        if (searchMemberPage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
            NetworkPage networkPage = homePage.clickNetworkMemeberPage();
            searchMemberPage = networkPage.searchWithEmptyQuery();
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("search"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.SEARCH_MEMBER);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    @Test
    public void test1() {
        assertTrue(searchMemberPage.containsMembersFoundText());
    }
}
