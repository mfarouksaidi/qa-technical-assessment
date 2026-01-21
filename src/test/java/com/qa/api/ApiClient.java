package com.qa.api;

import com.qa.common.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Base API Client for REST API interactions
 * Provides reusable methods for all HTTP operations
 */
public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;

    public ApiClient() {
        this.baseUrl = ConfigManager.getInstance().getApiBaseUrl();
        RestAssured.baseURI = baseUrl;
    }

    protected RequestSpecification getRequestSpecification() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }

    public Response get(String endpoint) {
        logger.info("GET request to: {}", endpoint);
        return getRequestSpecification()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        logger.info("GET request to: {} with params: {}", endpoint, queryParams);
        return getRequestSpecification()
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response post(String endpoint, Object body) {
        logger.info("POST request to: {} with body: {}", endpoint, body);
        return getRequestSpecification()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response put(String endpoint, Object body) {
        logger.info("PUT request to: {} with body: {}", endpoint, body);
        return getRequestSpecification()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response patch(String endpoint, Object body) {
        logger.info("PATCH request to: {} with body: {}", endpoint, body);
        return getRequestSpecification()
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(String endpoint) {
        logger.info("DELETE request to: {}", endpoint);
        return getRequestSpecification()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
