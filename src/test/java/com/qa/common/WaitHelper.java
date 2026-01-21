package com.qa.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Helper class for managing explicit waits
 */
public class WaitHelper {
    private static final Logger logger = LoggerFactory.getLogger(WaitHelper.class);
    private final WebDriverWait wait;

    public WaitHelper(WebDriver driver) {
        int timeout = ConfigManager.getInstance().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public void waitForElementVisible(WebElement element) {
        logger.debug("Waiting for element to be visible");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementInvisible(WebElement element) {
        logger.debug("Waiting for element to be invisible");
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForCondition(ExpectedCondition<?> condition) {
        logger.debug("Waiting for custom condition");
        wait.until(condition);
    }

    public void waitForPageLoad(WebDriver driver) {
        logger.debug("Waiting for page to load");
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public void waitForAjax(WebDriver driver) {
        logger.debug("Waiting for AJAX to complete");
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return jQuery.active == 0").equals(true));
    }

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted", e);
        }
    }
}
