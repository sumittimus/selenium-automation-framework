package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductPage {

	private static final Logger logger = LogManager.getLogger(ProductPage.class);

	private WebDriver driver;
	private WebDriverWait wait;

	// Locators
	private By quantityField = By.id("qty");
	private By addToCartButton = By.id("product-addtocart-button");
	private By cartIcon = By.cssSelector("a.action.showcart");
	private By sizeErrorMessage = By.cssSelector("div[id='super_attribute\\[143\\]-error']");
	private By colorErrorMessage = By.cssSelector("div[id='super_attribute\\[93\\]-error']");
	private By successMessage = By.xpath("//div[@class='message-success success message']");

	// Locators for Product Options (Size and Color) --- Since for each size and
	// color the last entry is changing.
	// Building an Helper function getSizeValue and getColorValue to solve this
	// problem
	private By sizeOption(String size) {
		return By.xpath("//div[@id='option-label-size-143-item-" + getSizeValue(size) + "']");
	}

	private By colorOption(String color) {
		return By.xpath("//div[@id='option-label-color-93-item-" + getColorValue(color) + "']");
	}

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// Select product size
	public void selectSize(String size) {
		WebElement sizeElement = wait.until(ExpectedConditions.elementToBeClickable(sizeOption(size)));
		sizeElement.click();
	}

	// Select product color
	public void selectColor(String color) {
		WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(colorOption(color)));
		colorElement.click();
	}

	// Set product quantity
	public void setQuantity(int quantity) {
		WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityField));
		qtyElement.clear();
		qtyElement.sendKeys(String.valueOf(quantity));
	}

	// Click button on the product page (Add to Cart)
	public void clickButton(String buttonName) {
		if (buttonName.equalsIgnoreCase("Add to Cart")) {
			WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
			addToCartBtn.click();
		}
	}

	// Navigate to shopping cart
	public void goToCart() {
		WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
		cart.click();
	}

	// Check if validation message is displayed for missing options
	public boolean isValidationMessageDisplayed() {
		try {
			WebElement sizeError = wait.until(ExpectedConditions.visibilityOfElementLocated(sizeErrorMessage));
			WebElement colorError = wait.until(ExpectedConditions.visibilityOfElementLocated(colorErrorMessage));
			return sizeError.isDisplayed() && colorError.isDisplayed();
		} catch (Exception e) {
			System.out.println("Validation messages not displayed within the expected time.");
			return false;
		}
	}

	// Method to verify success message
	public String getSuccessMessage() {
		WebElement successMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
		String message = successMsgElement.getText().trim();
		logger.info("Success message displayed: " + message);
		return message;
	}

	// Helper function to map size to value in the locator
	private String getSizeValue(String size) {
		switch (size.toUpperCase()) {
		case "XS":
			return "166";
		case "S":
			return "167";
		case "M":
			return "168";
		case "L":
			return "169";
		case "XL":
			return "170";
		default:
			throw new IllegalArgumentException("Invalid size: " + size);
		}
	}

	// Helper function to map color to value in the locator
	private String getColorValue(String color) {
		switch (color.toLowerCase()) {
		case "black":
			return "49";
		case "blue":
			return "50";
		case "red":
			return "51";
		default:
			throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
}
