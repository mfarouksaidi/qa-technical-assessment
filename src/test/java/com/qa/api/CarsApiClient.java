package com.qa.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cars-specific API Client
 * Provides domain-specific methods for Cars API endpoints
 */
public class CarsApiClient extends ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(CarsApiClient.class);
    private static final String CARS_ENDPOINT = "/cars";

    public Response getAllCars() {
        logger.info("Fetching all cars");
        return get(CARS_ENDPOINT);
    }

    public Response getCarById(int carId) {
        logger.info("Fetching car with ID: {}", carId);
        return get(CARS_ENDPOINT + "/" + carId);
    }

    public Response getHealthCheck() {
        logger.info("Fetching health check status");
        return get("/health-check");
    }
}
