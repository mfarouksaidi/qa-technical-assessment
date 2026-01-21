package com.qa.steps;

import com.qa.api.CarsApiClient;
import com.qa.common.ConfigManager;
import com.qa.common.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for API testing scenarios
 */
public class ApiSteps {
    private static final Logger logger = LoggerFactory.getLogger(ApiSteps.class);
    private final TestContext context;
    private final CarsApiClient carsApiClient;

    public ApiSteps(TestContext context) {
        this.context = context;
        this.carsApiClient = new CarsApiClient();
    }

    @Given("the API base URL is configured")
    public void theApiBaseUrlIsConfigured() {
        String baseUrl = ConfigManager.getInstance().getApiBaseUrl();
        logger.info("API Base URL: {}", baseUrl);
        assertThat(baseUrl).isNotNull().isNotEmpty();
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        Response response;
        if (endpoint.equals("/health-check")) {
            response = carsApiClient.getHealthCheck();
        } else if (endpoint.matches(".*/\\d+$")) {
            int id = Integer.parseInt(endpoint.substring(endpoint.lastIndexOf("/") + 1));
            response = carsApiClient.getCarById(id);
        } else {
            response = carsApiClient.getAllCars();
        }
        context.setApiResponse(response);
        logger.info("GET request sent to: {}, Status: {}", endpoint, response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = context.getApiResponse();
        assertThat(response.getStatusCode())
                .as("Response status code")
                .isEqualTo(expectedStatusCode);
        logger.info("Verified status code: {}", expectedStatusCode);
    }

    @Then("the response should contain a list of cars")
    public void theResponseShouldContainAListOfCars() {
        Response response = context.getApiResponse();
        List<?> cars = response.jsonPath().getList("cars");
        assertThat(cars)
                .as("Cars list")
                .isNotNull()
                .isNotEmpty();
        logger.info("Response contains {} cars", cars.size());
    }

    @Then("each car should have required fields")
    public void eachCarShouldHaveRequiredFields() {
        Response response = context.getApiResponse();
        List<Map<String, Object>> cars = response.jsonPath().getList("cars");

        for (Map<String, Object> car : cars) {
            assertThat(car).containsKeys("id", "name", "price", "image");
            logger.debug("Car validated: {}", car.get("id"));
        }
        logger.info("All cars have required fields");
    }

    @And("the response should contain a a message saying that the API is up")
    public void theResponseShouldContainAAMessageSayingThatTheAPIIsUp() {
        Response response = context.getApiResponse();
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage)
                .as("API Health Check Message")
                .isEqualToIgnoringCase("API is up!");

        String status = response.jsonPath().getString("status");
        assertThat(status).isEqualTo("UP");

        logger.info("Health check verified. Message: '{}', Status: '{}'", actualMessage, status);
    }
}
