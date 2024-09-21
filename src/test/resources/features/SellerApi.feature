Feature: Test Seller Api

  @getSellerVerifyEmailIsNotEmpty     @regression
  Scenario: get a single seller and veridy seller email is not empty
    Given user hits Get single seller api with "/api/myaccount/sellers/"
    Then verify seller email is not empty


    @getAllSellers        @regression
    Scenario: get all sellers and verify seller id is not 0
      Given user hits Get all seller api with "/api/myaccount/sellers"
      Then verify seller ids are not equal to 0

      @updateSeller       @regression
      Scenario: get single seller, update the seller, verify seller was updated
        Given user hits Get single seller api with "/api/myaccount/sellers/"
        Then verify seller email is not empty
        Then user hits put api with "/api/myaccount/sellers/"
        Then verify seller email was updated
        And verify user first name was updated
        And verify user first name was updated


        @archiveSeller      @regression
        Scenario: get a single seller, archive the seller, and verify that seller has been archived
          Given user hits Get single seller api with "/api/myaccount/sellers/"
          Then user hits archive api with endpoint "/api/myaccount/sellers/archive/unarchive"
          Then user hits get all seller api with "/api/myaccount/sellers"

          @createDelete       @regression
          Scenario: create a seller, verify seller was created, delete the seller and verify seller was created
            Given user hits post api with "/api/myaccount/sellers"
            Then verify seller id was generated
            Then verify seller name is not empty
            And verify seller email is not empty
            Then delete the seller with "/api/myaccount/sellers/"
            Then user hits Get all seller api with "/api/myaccount/sellers"
            Then verify deleted seller is not in the list










