@sidebar
Feature: Sidebar Menu Functionality
  As a logged in user
  I want to use the sidebar menu for navigation and app management
  So that I can access different sections and control the application state

  Background:
    Given I am logged in as a standard user

  @TC_022
  Scenario: Verify Logout functionality
    When I open the sidebar menu
    And I click the "Logout" link
    Then I should be redirected to the login page
    And I should not be able to access inventory page directly

  @TC_023
  Scenario: Verify 'Reset App State' functionality
    Given I have added "Sauce Labs Backpack" to the cart
    And the cart badge shows 1 item
    When I open the sidebar menu
    And I click the "Reset App State" link
    And I close the sidebar menu
    Then the cart badge should disappear
    And the "Add to cart" button should be reset for all products
