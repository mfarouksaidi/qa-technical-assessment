package com.qa.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CarsShowroomPage extends BasePage {

    @FindBy(css = "h1")
    private WebElement pageHeading;

    @FindBy(css = ".filter-make")
    private WebElement makeFilter;

    @FindBy(css = ".filter-model")
    private WebElement modelFilter;

    @FindBy(css = ".filter-year")
    private WebElement yearFilter;

    @FindBy(css = "[data-testid='car-name']")
    private List<WebElement> carMakes;

    @FindBy(css = "[data-testid='car-price']")
    private List<WebElement> carPrices;

    public CarsShowroomPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String baseUrl) {
        String url = baseUrl;
        logger.info("Navigating to Cars Showroom: {}", url);
        driver.get(url);
        waitForPageLoad();
    }

    public String getPageHeading() {
        return getText(pageHeading);
    }

    public boolean isPageLoaded() {
        try {
            waitForElementToBeVisible(pageHeading);
            return true;
        } catch (Exception e) {
            logger.error("Page not loaded", e);
            return false;
        }
    }

    public int getCarCount() {
        try {
            List<WebElement> allCars = driver.findElements(By.cssSelector("[data-testid^='car-']"));

            if (!allCars.isEmpty()) {
                return allCars.size();
            }

            List<WebElement> backupCards = driver.findElements(By.cssSelector(".card"));
            return backupCards.size();

        } catch (Exception e) {
            logger.error("Error counting cars: " + e.getMessage(), e);
            return 0;
        }
    }

    public List<String> getCarMakes() {
        return carMakes.stream()
                .map(this::getText)
                .collect(Collectors.toList());
    }

    public List<String> getCarPrices() {
        return carPrices.stream()
                .map(this::getText)
                .collect(Collectors.toList());
    }

}
