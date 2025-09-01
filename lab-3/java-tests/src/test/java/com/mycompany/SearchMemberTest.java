package com.mycompany;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

@Disabled
public class SearchMemberTest extends LocalTestBase {
    private NetworkPage networkPage;

    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        
        if (networkPage == null) {
            LoginPage loginPage = (LoginPage) new LoginPage(getDriver()).acceptAllPrivacy();
            HomePage homePage = loginPage.loginWithValidCredentials(TEST_USERNAME, TEST_PASSWORD);
            networkPage = homePage.clickNetworkMemeberPage();
        }

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("network"));
        } catch (TimeoutException e) {
            if (getDriver().getCurrentUrl().contains("login")) {
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
    }

    private void resetApplicationState() {
        getDriver().get(AppUrls.NETWORK);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Stephan", "Müller", "Alexander"})
    public void givenValidMemberName_whenSearchingMembers_thenShouldDisplayCorrectNameInResults(String memberName) {
        SearchMemberPage searchMemberPage = networkPage.searchMember(memberName);
        
        assertTrue(searchMemberPage.containsMembersFoundText());
        assertTrue(searchMemberPage.areSearchResultsDisplayed());
        
        String resultName = searchMemberPage.getFirstMemberName();
        assertNotNull(resultName);
        assertTrue(resultName.contains(memberName));
    }

    @Test
    public void givenSearchWithEmptyQuery_whenSearchingMembers_thenResultShouldBeEmpty() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        String memberText = searchMemberPage.getMemberFoundText();
        
        getDriver().navigate().refresh();
        searchMemberPage.waitForPageToLoad();
        
        assertEquals(memberText, "No members found");
    }

    @Test
    public void givenInvalidMemberName_whenSearchingMembers_thenShouldDisplayNoResultsMessage() {
        String invalidName = "NonexistentUser12345";
        
        SearchMemberPage searchMemberPage = networkPage.searchMember(invalidName);
        
        assertEquals(searchMemberPage.getMemberFoundText(), "No members found");
    }

    @Test
    public void givenSearchResultsDisplayed_whenCheckingFilters_thenShouldShowNewestAndDirectContactsFilters() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        assertTrue(searchMemberPage.isNewestFilterDisplayed());
        assertTrue(searchMemberPage.isDirectContactsFilterDisplayed());
    }

    @Test
    public void givenSearchResultsDisplayed_whenClickingNewestFilter_thenShouldActivateNewestFilter() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        searchMemberPage.clickNewestFilter();
        
        assertTrue(searchMemberPage.isNewestFilterActive());
        assertFalse(searchMemberPage.isDirectContactsFilterActive());
    }

    @Test
    public void givenSearchResultsDisplayed_whenClickingDirectContactsFilter_thenShouldActivateDirectContactsFilter() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        searchMemberPage.clickDirectContactsFilter();
        
        assertTrue(searchMemberPage.isDirectContactsFilterActive());
        assertFalse(searchMemberPage.isNewestFilterActive());
    }

    @Test
    public void givenSearchPerformed_whenClickOnGoToHomepage_thenSouldDisplayHomepage() {
        SearchMemberPage searchMemberPage = networkPage.searchMember("Thomas");
        HomePage homePage = searchMemberPage.clickGoToHomepage();

        assertEquals(homePage.getWelcomeMessage(), "Tell the XING AI about the job you want");
    }
}