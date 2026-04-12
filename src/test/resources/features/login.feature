@login
Feature: SauceDemo Login Functionality
  As a user of SauceDemo
  I want to be able to login to the application
  So that I can access the products page

  @smoke @TC_001
  Scenario: Valid user login
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be redirected to the products page

  @negative @TC_002
  Scenario: Login failure with invalid password
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "wrong_password"
    And I click the login button
    Then I should see an error message
    And the error should contain "Username and password do not match"

  @negative @TC_003
  Scenario: Login behavior for locked out user
    Given I am on the SauceDemo login page
    When I enter username "locked_out_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And the error should contain "Sorry, this user has been locked out"

  @negative @TC_004
  Scenario: Login validation with empty fields
    Given I am on the SauceDemo login page
    When I click the login button
    Then I should see an error message
    And the error should contain "Username is required"

  @smoke @TC_025
  Scenario: Verify login inputs against SQL Injection
    Given I am on the SauceDemo login page
    When I enter username "' OR 1=1 --"
    And I enter password "pwd"
    And I click the login button
    Then I should see an error message
    And the error should contain "Username and password do not match"

  @security @TC_026
  Scenario: Verify unauthorized access prevention
    Given I try to access the inventory page directly without login
    Then I should be redirected to the login page
    And I should see an error message about authorization