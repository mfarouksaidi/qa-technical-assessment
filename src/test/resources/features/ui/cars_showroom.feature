@ui @cars
Feature: Cars Showroom UI Testing
  As a user
  I want to browse cars in the showroom
  So that I can find and view car details

  Background:
    Given I am on the Cars Showroom page

  @smoke
  Scenario: Verify Cars Showroom page loads successfully
    Then I should see the page heading
    And I should see a list of cars displayed

  Scenario: Verify car cards display correct information
    When the page loads
    Then each car card should display:
      | Make   |
      | Price  |