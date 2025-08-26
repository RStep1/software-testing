package com.mycompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobDetailsPage extends BasePage {
    
    private By jobTitleLocator = By.xpath("//div[@data-testid='job-details-title']//h1");
    private By companyNameLocator = By.xpath("//p[@data-testid='job-details-company-info-name']");
    
    public JobDetailsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }
    
    public boolean isPageLoaded() {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(jobTitleLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getJobTitle() {
        return getText(jobTitleLocator);
    }
    
    public String getCompanyName() {
        return getText(companyNameLocator);
    }

    public void closeAndReturnToPreviousTab(String originalWindowHandle) {
        getDriver().close();
        getDriver().switchTo().window(originalWindowHandle);
    }
}