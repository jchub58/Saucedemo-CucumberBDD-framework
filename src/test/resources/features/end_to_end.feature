@end_to_end
Feature: End-to-End User Journey
  As a customer
  I want to complete a full shopping experience
  So that I can successfully purchase products

  @TC_024
  Scenario: Verify complete successful purchase lifecycle
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be redirected to the products page
    When I sort products by "Price (low to high)"
    And I add the cheapest product to the cart
    And I go to the cart page
    And I proceed to checkout
    And I enter checkout information:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
    And I continue to overview
    And I finish the order
    Then I should see the order confirmation page
    And I should see "Thank you for your order!"
    When I open the sidebar menu
    And I click the "Logout" link
    Then I should be redirected to the login page

  @TC_031
  Scenario: Verify session handling across multiple browser tabs
    Given I am logged in as a standard user in the main tab
    When I open a new browser tab and navigate to the inventory page
    Then I should see the products page in the new tab
    When I logout from the main tab
    And I try to add a product to the cart in the new tab
    Then I should be redirected to the login page
    And I should see an error about expired session
