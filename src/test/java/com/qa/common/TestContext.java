package com.qa.common;

import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Context for sharing state between Cucumber step definitions
 * Uses dependency injection via PicoContainer
 */
public class TestContext {
    private WebDriver driver;
    private Response apiResponse;
    private final Map<String, Object> scenarioContext;

    public TestContext() {
        this.scenarioContext = new HashMap<>();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public Response getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(Response apiResponse) {
        this.apiResponse = apiResponse;
    }

    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getContext(String key) {
        return scenarioContext.get(key);
    }

    public Boolean containsKey(String key) {
        return scenarioContext.containsKey(key);
    }

    public void clearContext() {
        scenarioContext.clear();
    }
}
