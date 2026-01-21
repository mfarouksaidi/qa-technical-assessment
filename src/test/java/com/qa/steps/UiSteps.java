package com.qa.steps;

import com.qa.common.ConfigManager;
import com.qa.common.DriverManager;
import com.qa.common.TestContext;
import com.qa.ui.pages.CarsShowroomPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UiSteps {
    private static final Logger logger = LoggerFactory.getLogger(UiSteps.class);
    private final TestContext context;
    private WebDriver driver;
    private CarsShowroomPage carsShowroomPage;

    public UiSteps(TestContext context) {
        this.context = context;
    }

    private void initializeDriver() {
        if (driver == null) {
            driver = DriverManager.getDriver();
            context.setDriver(driver);
            carsShowroomPage = new CarsShowroomPage(driver);
        }
    }

    @Given("I am on the Cars Showroom page")
    public void iAmOnTheCarsShowroomPage() {
        initializeDriver();
        String baseUrl = ConfigManager.getInstance().getBaseUrl();
        carsShowroomPage.navigateTo(baseUrl);
        logger.info("Navigated to Cars Showroom page");
    }

    @When("the page loads")
    public void thePageLoads() {
        assertThat(carsShowroomPage.isPageLoaded())
                .as("Page loaded successfully")
                .isTrue();
        logger.info("Page loaded successfully");
    }

    @Then("I should see the page heading")
    public void iShouldSeeThePageHeading() {
        String heading = carsShowroomPage.getPageHeading();
        assertThat(heading)
                .as("Page heading")
                .isNotNull()
                .isNotEmpty();
        logger.info("Page heading: {}", heading);
    }

    @Then("I should see a list of cars displayed")
    public void iShouldSeeAListOfCarsDisplayed() {
        int carCount = carsShowroomPage.getCarCount();
        assertThat(carCount)
                .as("Number of cars displayed")
                .isGreaterThan(0);
        logger.info("Number of cars displayed: {}", carCount);
    }

    @Then("each car card should display:")
    public void eachCarCardShouldDisplay(DataTable dataTable) {
        List<String> expectedFields = dataTable.asList();
        logger.info("Validating car cards display: {}", expectedFields);

        if (expectedFields.contains("Make")) {
            assertThat(carsShowroomPage.getCarMakes()).isNotEmpty();
        }
        if (expectedFields.contains("Price")) {
            assertThat(carsShowroomPage.getCarPrices()).isNotEmpty();
        }
        logger.info("All required fields are displayed");
    }

}
