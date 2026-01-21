@api @cars
Feature: Cars API Testing
  As a tester
  I want to test the Cars API endpoints
  So that I can ensure the API works correctly

  Background:
    Given the API base URL is configured

  @smoke @get
  Scenario: Get health-check to see if server is running
    When I send a GET request to "/health-check"
    Then the response status code should be 200
    And the response should contain a a message saying that the API is up

  @smoke @get
  Scenario: Get all cars successfully
    When I send a GET request to "/cars"
    Then the response status code should be 200
    And the response should contain a list of cars
    And each car should have required fields
