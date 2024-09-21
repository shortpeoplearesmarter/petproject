Feature: Invoice Functionality

  @invoice
  Scenario: Verify Get and POst endpoints
    Given base url
    And user provides valid authorization token
    And user store parameters "1" and "50" in a Hashmap
    And user hits GET endpoint "/api/myaccount/invoices/seller"
    Then user verifies status code is 200
    And user gets back to base url
    And user creates new invoice
    And user hits POST endpoint "/api/myaccount/invoices"
    And user validates the status code is 201 and that new invoice has been created


