package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import io.cucumber.java.en.*;

public class APISteps {
    private Response response;

    @Given("I fetch store inventory")
    public void getStoreInventory() {
        response = RestAssured.get("https://petstore.swagger.io/v2/store/inventory");
    }

    @Then("I validate the response status code is {int}")
    public void validateStatusCode(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    @Then("I validate the 'pending' count")
    public void validatePendingCount() {
        int pendingCount = response.jsonPath().getInt("pending");
        Assert.assertTrue(pendingCount > 0, "Pending count should be greater than 0");
    }
}
