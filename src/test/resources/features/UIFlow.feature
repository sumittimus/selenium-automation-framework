Feature: Magento Store Testing

  Scenario: Add product to cart and verify details
    Given I open the e-store
    When I search for product "Hero Hoodie"
    And I add the product with Size "L", Color "Black", Quantity 2
    Then I receive success message "You added Hero Hoodie to your shopping cart."
    And I navigate to the cart
    Then I verify product name and publish the total price on the cart page


  Scenario: Add product without selecting options and verify error
    Given I open the e-store
    When I search for product "Hero Hoodie"
    And I add the product to cart
    Then I should see error message "This is a required field."
