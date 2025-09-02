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

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@Disabled
@ExtendWith(AllureJunit5.class)
@Epic("Network")
@Feature("Member Search Functionality")
public class SearchMemberTest extends LocalTestBase {
    private NetworkPage networkPage;

    private static final String TEST_USERNAME = TestConfig.getUsername();
    private static final String TEST_PASSWORD = TestConfig.getPassword();
    
    @BeforeEach
    @Override
    @Step("Setup test environment and navigate to network page")
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
                takeScreenshot("login-failed-network");
                throw new RuntimeException("Login failed - still on login page");
            }
        }

        resetApplicationState();
        allureAddEnvironmentInfo("Network URL", AppUrls.NETWORK);
    }

    @Step("Reset application state")
    private void resetApplicationState() {
        getDriver().get(AppUrls.NETWORK);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(
            d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Stephan", "Müller", "Alexander"})
    @Description("Test member search with different valid names")
    public void givenValidMemberName_whenSearchingMembers_thenShouldDisplayCorrectNameInResults(String memberName) {
        SearchMemberPage searchMemberPage = networkPage.searchMember(memberName);
        
        assertTrue(searchMemberPage.containsMembersFoundText());
        assertTrue(searchMemberPage.areSearchResultsDisplayed());
        
        String resultName = searchMemberPage.getFirstMemberName();
        assertNotNull(resultName);
        assertTrue(resultName.contains(memberName));
        takeScreenshot("member-search-" + memberName);
        allureAddLog("Member search completed for: " + memberName);
    }

    @Test
    @Description("Test member search with empty query")
    public void givenSearchWithEmptyQuery_whenSearchingMembers_thenResultShouldBeEmpty() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        String memberText = searchMemberPage.getMemberFoundText();
        
        getDriver().navigate().refresh();
        searchMemberPage.waitForPageToLoad();
        
        assertEquals(memberText, "No members found");
        takeScreenshot("empty-query-search");
        allureAddLog("Empty query search executed - no members found");
    }

    @Test
    @Description("Test member search with invalid name")
    public void givenInvalidMemberName_whenSearchingMembers_thenShouldDisplayNoResultsMessage() {
        String invalidName = "NonexistentUser12345";
        
        SearchMemberPage searchMemberPage = networkPage.searchMember(invalidName);
        
        assertEquals(searchMemberPage.getMemberFoundText(), "No members found");
        takeScreenshot("invalid-member-search");
        allureAddLog("Invalid member search: " + invalidName);
    }

    @Test
    @Description("Test availability of search filters")
    public void givenSearchResultsDisplayed_whenCheckingFilters_thenShouldShowNewestAndDirectContactsFilters() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        assertTrue(searchMemberPage.isNewestFilterDisplayed());
        assertTrue(searchMemberPage.isDirectContactsFilterDisplayed());
        takeScreenshot("search-filters-displayed");
        allureAddLog("Search filters are displayed correctly");
    }

    @Test
    @Description("Test newest filter functionality")
    public void givenSearchResultsDisplayed_whenClickingNewestFilter_thenShouldActivateNewestFilter() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        searchMemberPage.clickNewestFilter();
        
        assertTrue(searchMemberPage.isNewestFilterActive());
        assertFalse(searchMemberPage.isDirectContactsFilterActive());
        takeScreenshot("newest-filter-active");
        allureAddLog("Newest filter activated successfully");
    }

    @Test
    @Description("Test direct contacts filter functionality")
    public void givenSearchResultsDisplayed_whenClickingDirectContactsFilter_thenShouldActivateDirectContactsFilter() {
        SearchMemberPage searchMemberPage = networkPage.searchWithEmptyQuery();
        
        searchMemberPage.clickDirectContactsFilter();
        
        assertTrue(searchMemberPage.isDirectContactsFilterActive());
        assertFalse(searchMemberPage.isNewestFilterActive());
        takeScreenshot("direct-contacts-filter-active");
        allureAddLog("Direct contacts filter activated successfully");
    }

    @Test
    @Description("Test navigation to homepage from search results")
    public void givenSearchPerformed_whenClickOnGoToHomepage_thenSouldDisplayHomepage() {
        SearchMemberPage searchMemberPage = networkPage.searchMember("Thomas");
        HomePage homePage = searchMemberPage.clickGoToHomepage();

        assertEquals(homePage.getWelcomeMessage(), "Tell the XING AI about the job you want");
        takeScreenshot("homepage-navigation-from-search");
        allureAddLog("Navigated to homepage from search results");
    }
}