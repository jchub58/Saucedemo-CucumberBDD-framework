@checkout
Feature: Checkout Process
  As a customer with items in cart
  I want to complete the checkout process
  So that I can purchase my items

  Background:
    Given I am logged in as a standard user
    And I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page

  @TC_013
  Scenario: Verify cart page displays added items correctly
    Given I am logged in as a standard user
    And I have added "Sauce Labs Backpack" to the cart
    And I have added "Sauce Labs Bike Light" to the cart
    When I go to the cart page
    Then I should see 2 items in the cart
    And I should see "Sauce Labs Backpack" in the cart
    And I should see "Sauce Labs Bike Light" in the cart

  @TC_014
  Scenario: Verify 'Continue Shopping' functionality
    Given I am logged in as a standard user
    And I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page
    When I click the "Continue Shopping" button
    Then I should be redirected to the products page
    And the cart should still contain "Sauce Labs Backpack"

  @TC_015
  Scenario: Remove item from Cart page
    Given I am logged in as a standard user
    And I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page
    When I remove "Sauce Labs Backpack" from the cart
    Then the cart should be empty
    And the cart badge should show 0 items

  @TC_016
  Scenario: Proceed to checkout
    Given I am logged in as a standard user
    And I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page
    When I proceed to checkout
    Then I should be on the checkout information page

  @TC_017
  Scenario: Verify checkout validation with empty user info
    Given I am on the checkout information page
    When I click the "Continue" button without entering information
    Then I should see error "First Name is required"

  @TC_018
  Scenario: Submit valid checkout information
    Given I am on the checkout information page
    When I enter checkout information:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
    And I click the "Continue" button
    Then I should be on the checkout overview page

  @TC_019
  Scenario: Verify Checkout Overview calculations
    Given I am on the checkout overview page with items "Sauce Labs Backpack" and "Sauce Labs Bike Light"
    Then I should see the correct item total
    And I should see the calculated tax
    And I should see the correct total amount

  @TC_020
  Scenario: Complete the purchase flow
    Given I am on the checkout overview page
    When I click the "Finish" button
    Then I should see the order confirmation page
    And I should see "Thank you for your order!"

  @TC_021
  Scenario: Verify 'Back Home' button post-purchase
    Given I am on the order confirmation page
    When I click the "Back Home" button
    Then I should be redirected to the products page
    And the cart should be empty

  @TC_028
  Scenario: Verify checkout fields against script injection
    Given I am on the checkout information page
    When I enter first name "<script>alert(1)</script>"
    And I enter last name "Doe"
    And I enter postal code "12345"
    And I click the "Continue" button
    Then I should be on the checkout overview page
    And the script should be treated as plain text

  @TC_029
  Scenario: Verify checkout fields with extremely long strings
    Given I am on the checkout information page
    When I enter first name with 500+ characters
    And I enter last name "Doe"
    And I enter postal code "12345"
    And I click the "Continue" button
    Then the system should handle the long input gracefully

  @TC_030
  Scenario: Verify browser 'Back' button behavior after successful purchase
    Given I have completed a purchase
    When I click the browser back button
    Then I should not be able to duplicate the order
    And I should be redirected to the inventory page or see an empty cart

  Scenario: Complete checkout with valid information
    When I proceed to checkout
    And I enter checkout information:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
    And I continue to overview
    Then I should see the order summary
    When I finish the order
    Then I should see the order confirmation
    And I should see "Thank you for your order!"

  Scenario: Checkout validation - missing first name
    When I proceed to checkout
    And I enter checkout information:
      | firstName | lastName | postalCode |
      |           | Doe      | 12345      |
    And I continue to overview
    Then I should see error "First Name is required"

  Scenario: Checkout validation - missing postal code
    When I proceed to checkout
    And I enter checkout information:
      | firstName | lastName | postalCode |
      | John      | Doe      |            |
    And I continue to overview
    Then I should see error "Postal Code is required"