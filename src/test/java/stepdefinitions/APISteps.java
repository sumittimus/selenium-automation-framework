package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class APISteps {

	// Using errorMessages to report errors while avoiding the build failure
	private static List<String> errorMessages = new ArrayList<>();
	private Response response;

	@Given("I fetch store inventory")
	public void getStoreInventory() {
		try {
			response = RestAssured.get("https://petstore.swagger.io/v2/store/inventory");
		} catch (Exception e) {
			errorMessages.add("Error Details : " + e.getMessage());
		}

	}

	@Then("I validate the response status code is {int}")
	public void validateStatusCode(int expectedStatus) {
		try {
			Assert.assertEquals(response.getStatusCode(), expectedStatus);
		} catch (Exception e) {
			errorMessages.add("Different error code received : " + response.getStatusCode() + ". Fatal Error Details : "
					+ e.getMessage());
		}

	}

	@Then("I validate the 'pending' count")
	public void validatePendingCount() {
			int pendingCount = response.jsonPath().getInt("pending");
			Assert.assertTrue(pendingCount > 0, "Pending count should be greater than 0");
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
