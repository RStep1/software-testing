package com.mycompany.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public abstract class BasePage {
    private WebDriver driver;
    private WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public BasePage acceptAllPrivacy() {
        try {
            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#usercentrics-root")
            ));
            Thread.sleep(1000);
            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement acceptButton = null;
            try {
                acceptButton = shadowRoot.findElement(
                    By.cssSelector("button[data-testid='uc-accept-all-button']")
                );
            } catch (Exception e) {
                acceptButton = shadowRoot.findElement(
                    By.cssSelector("button:contains('Accept all'), button:contains('Accept All')")
                );
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptButton);
            wait.until(ExpectedConditions.invisibilityOf(shadowHost));

        } catch (Exception e) {
            System.out.println("Privacy banner handling failed: " + e.getMessage());
            try {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
                new Actions(driver).moveByOffset(100, 100).click().perform();
            } catch (Exception ex) {
                System.out.println("Alternative method also failed: " + ex.getMessage());
            }
        }
        return this;
    }

    public void waitForPageToLoad() {
        getWait().until(d -> {
            try {
                return ((JavascriptExecutor) d)
                    .executeScript("return document.readyState")
                    .equals("complete");
            } catch (Exception e) {
                return false;
            }
        });
    }

    // protected void click(WebElement element) {
    //     wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    // }

    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    protected void waitForElementToBeStable(By locator) {
        wait.until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                return element.isDisplayed() && element.isEnabled();
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }
    
    protected void safeType(By locator, String text) {
        waitForElementToBeStable(locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isFieldEmpty(By locator) {
        waitForElementToBeStable(locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getAttribute("value").isEmpty();
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementLocatorVisible(By locator) {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        return wait;
    }

    protected void waitForNewWindowAndSwitch(String originalWindowHandle) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!windowHandle.equals(originalWindowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }
        
        waitForPageToLoad();
    }
    
    protected void switchToWindow(String windowHandle) {
        getDriver().switchTo().window(windowHandle);
    }
    
    protected String getCurrentWindowHandle() {
        return getDriver().getWindowHandle();
    }
}

