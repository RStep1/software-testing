package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class SearchMemberPage extends BasePage {
    private static final By MEMBERS_COUNT_HEADLINE_LOCATOR = 
        By.xpath("//h2[@data-xds='Headline' and contains(text(), 'members found')]");

    public SearchMemberPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public boolean isMembersCountDisplayed() {
        return isElementLocatorVisible(MEMBERS_COUNT_HEADLINE_LOCATOR);
    }
    
    public boolean containsMembersFoundText() {
        try {
            String text = getText(MEMBERS_COUNT_HEADLINE_LOCATOR);
            return text.contains("members found");
        } catch (Exception e) {
            return false;
        }
    }
}
