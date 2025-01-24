Feature: Pet Store API Testing

  Scenario: Validate store inventory
    Given I fetch store inventory
    Then I validate the response status code is 200
    And I validate the 'pending' count
