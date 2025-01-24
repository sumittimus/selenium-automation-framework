package stepdefinitions;

import drivers.BrowserFactory;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import config.ConfigReader;
import pages.HomePage;
import pages.ProductPage;
import pages.CartPage;

public class UIFlowSteps {
	private WebDriver driver;
	private HomePage homePage;
	private ProductPage productPage;
	private CartPage cartPage;

	// Using errorMessages to report errors while avoiding the build failure
	private static List<String> errorMessages = new ArrayList<>();

	@Given("I open the e-store")
	public void i_open_the_estore() {
		try {
			driver = BrowserFactory.getBrowser();
			driver.get(ConfigReader.getProperty("baseUrl"));
			homePage = new HomePage(driver);
		} catch (Exception e) {
			errorMessages.add("Error in opening e-store: " + e.getMessage());
		}
	}

	@When("I search for product {string}")
	public void i_search_for_product(String productName) {
		try {
			homePage.searchForProduct(productName);
			productPage = new ProductPage(driver);
		}catch (Exception e) {
			errorMessages.add("Error in searching product: " + e.getMessage());
		}
	}

	@When("I add the product with Size {string}, Color {string}, Quantity {int}")
	public void i_add_the_product_with_size_color_quantity(String size, String color, Integer quantity) {
		try{
			productPage.selectSize(size);
			productPage.selectColor(color);
			productPage.setQuantity(quantity);
			productPage.clickButton("Add to Cart");
		}catch (Exception e) {
			errorMessages.add("Error encountered while adding product to the cart : " +e.getMessage());
		}
	}

	// Scenario 2
	@When("I add the product to cart")
	public void i_add_the_product_to_cart() {
		try {
			productPage.clickButton("Add to Cart");	
		}catch (Exception e) {
			errorMessages.add("Error encountered while adding product to the cart : " +e.getMessage());
		}
		
	}

	@Then("I receive success message {string}")
	public void i_should_see_success_message(String expectedMessage) {
		try {
			String actualMessage = productPage.getSuccessMessage();
			Assert.assertTrue(actualMessage.contains(expectedMessage),
					"Success message does not match. Expected: " + expectedMessage + ", but got: " + actualMessage);	
		}catch (Exception e) {
			errorMessages.add("Success Message not received. Seems some fatal error : "+ e.getMessage());
		}
		
	}

	@Then("I navigate to the cart")
	public void i_navigate_to_the_cart() {
		try {
			productPage.goToCart();
			cartPage = new CartPage(driver);	
		}catch (Exception e) {
			errorMessages.add("Failed to navigate to Cart Page. Error Message : "+e.getMessage());
		}
		
	}

	// Scenario 1
	@Then("I verify product name and publish the total price on the cart page")
	public void i_verify_product_name_and_publish_total_price_on_cart_page() {
		String expectedProductName = "Hero Hoodie";
		try {
			// Verify if the product is present in the cart
			boolean isProductPresent = cartPage.isProductPresent(expectedProductName);
			Assert.assertTrue(isProductPresent, "Product '" + expectedProductName + "' not found in the cart.");

			// Retrieve and publish the total price
			double totalPrice = cartPage.getGrandTotal();
			System.out.println("Total Price on Cart Page: $" + totalPrice);
	
		}catch (Exception e) {
			errorMessages.add("Error received while retrieving the total price and product name. Error details : "+e.getMessage());
		}
			}

	// Scenario 2 : Add product without selecting options and verify error
	@Then("I should see error message {string}")
	public void i_should_see_error_message(String expectedMessage) {
		try {
			boolean isErrorDisplayed = productPage.isValidationMessageDisplayed();
			Assert.assertTrue(isErrorDisplayed, "Expected validation message was not displayed.");
			System.out.println("Validation message displayed: " + expectedMessage);	
		}catch (Exception e) {
			errorMessages.add("Failed to retrieve the error message for adding item without selections. Error Message : "+e.getMessage());
		}
		
	}

	
	@After
    public void reportErrors() {
        if (!errorMessages.isEmpty()) {
            System.out.println("Test execution completed with the following errors:");
            for (String error : errorMessages) {
                System.out.println(error);
            }
            Assert.fail("Test execution completed with errors. Check logs for details.");
        }
    }
}
