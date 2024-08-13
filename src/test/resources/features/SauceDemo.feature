
Feature: Login to Sauce Demo


  @Login
  Scenario: verify user is logged in
    Given user is on the webpage
    When user enters username in the username field
    And user enters password in the password field
    Then user clicks on Login button
    And verify user is logged in