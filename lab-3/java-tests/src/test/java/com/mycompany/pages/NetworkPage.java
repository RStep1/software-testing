package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetworkPage extends BasePage {
    private static final By SEARCH_MEMBER_INPUT_LOCATOR = By.xpath("//input[@placeholder='Member search']");
    
    public NetworkPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public SearchMemberPage searchMember(String memberName) {
        typeAndPressEnter(SEARCH_MEMBER_INPUT_LOCATOR, memberName);
        return navigateToSearchMemberPage();
    }

    public SearchMemberPage searchWithEmptyQuery() {
        pressEnterOnElement(SEARCH_MEMBER_INPUT_LOCATOR);
        return navigateToSearchMemberPage();
    }
    
    private SearchMemberPage navigateToSearchMemberPage() {
        // return new SearchMemberPage(getDriver());
        SearchMemberPage searchMemberPage = new SearchMemberPage(getDriver());
        waitForPageToLoad();
        return searchMemberPage;
    }
}
