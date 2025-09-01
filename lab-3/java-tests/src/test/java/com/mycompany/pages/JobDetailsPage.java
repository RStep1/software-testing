package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobDetailsPage extends BasePage {
    
    private static final By JOB_TITLE_LOCATOR = By.xpath("//div[@data-testid='job-details-title']//h1");
    private static final By COMPANY_NAME_LOCATOR = By.xpath("//p[@data-testid='job-details-company-info-name']");
    
    public JobDetailsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }
    
    public boolean isPageLoaded() {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(JOB_TITLE_LOCATOR)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getJobTitle() {
        return getText(JOB_TITLE_LOCATOR);
    }
    
    public String getCompanyName() {
        return getText(COMPANY_NAME_LOCATOR);
    }

    public void closeAndReturnToPreviousTab(String originalWindowHandle) {
        getDriver().close();
        getDriver().switchTo().window(originalWindowHandle);
    }
}