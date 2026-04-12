@products
Feature: Products and Shopping Cart
  As a logged in user
  I want to browse products and manage my cart
  So that I can purchase items

  Background:
    Given I am logged in as a standard user

  @TC_005
  Scenario: Verify UI elements on the Inventory Page
    Then I should see the products page
    And I should see 6 products listed
    And I should see the cart icon
    And I should see the sorting dropdown

  @TC_006
  Scenario: Verify product sorting by Name Z to A
    When I sort products by "Name (Z to A)"
    Then the products should be sorted by name descending

  @TC_007
  Scenario: Verify product sorting by Price Low to High
    When I sort products by "Price (low to high)"
    Then the products should be sorted by price ascending

  @TC_008
  Scenario: Add a single product to cart from PLP
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1 item

  @TC_009
  Scenario: Remove a product from cart from PLP
    Given I have added "Sauce Labs Backpack" to the cart
    When I remove "Sauce Labs Backpack" from the products page
    Then the cart badge should show 0 items

  @TC_010
  Scenario: Verify navigation to Product Detail Page
    When I click on product "Sauce Labs Fleece Jacket"
    Then I should see the product detail page
    And I should see the product image
    And I should see the product description
    And I should see the product price

  @TC_011
  Scenario: Add product to cart from PDP
    When I click on product "Sauce Labs Fleece Jacket"
    And I click the "Add to cart" button on the product detail page
    Then the cart badge should show 1 item

  @TC_012
  Scenario: Verify 'Back to products' button
    When I click on product "Sauce Labs Fleece Jacket"
    And I click the "Back to products" button
    Then I should be redirected to the products page

  @TC_027
  Scenario: Verify UI stability during rapid 'Add to cart' clicks
    When I rapidly click "Add to cart" on "Sauce Labs Backpack"
    Then the cart badge should show only 1 item
    And the application should not crash