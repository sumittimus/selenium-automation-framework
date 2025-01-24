package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
	private WebDriver driver;
	private WebDriverWait wait;

	// Updated locators based on provided values
	private By productName = By.cssSelector("a[data-bind='attr: {href: product_url}, html: product_name}']");
	private By productItemDetails = By.cssSelector("div[class='product-item-details']");
	private By productPrice = By.cssSelector("span[class='minicart-price'] span[class='price']");
	private By quantityField = By.cssSelector(".count");
	private By updateCartButton = By.xpath("//span[normalize-space()='View and Edit Cart']");
	private By grandTotal = By.cssSelector("span[data-bind='html: cart().subtotal_excl_tax'] span[class='price']");

	public CartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// Get the product name from the cart page (Couldn't implement it since relying
	// on isProductPresent function. Build it as a backup.)
	public String getProductName() {
		// Wait for product item details to be visible
		List<WebElement> productElements = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productItemDetails));

		if (productElements.isEmpty()) {
			throw new RuntimeException("No products found in the cart.");
		}

		// Extract product name from the first product in the cart
		WebElement firstProduct = productElements.get(0);
		WebElement productLink = firstProduct.findElement(productName);

		return productLink.getText().trim();
	}

	// Check if a product with the given name exists in the cart
	public boolean isProductPresent(String expectedProductName) {
		List<WebElement> productElements = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productItemDetails));

		for (WebElement productElement : productElements) {
			String productText = productElement.getText().trim();
			if (productText.contains(expectedProductName)) {
				return true;
			}
		}
		return false;
	}

	// Get the product price from the cart
	public double getProductPrice() {
		WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
		String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
		return Double.parseDouble(priceText);
	}

	// Get the product quantity from the cart
	public int getProductQuantity() {
		WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityField));
		return Integer.parseInt(qtyElement.getText().trim());
	}

	// Click the "View and Edit Cart" button
	public void updateCart() {
		WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(updateCartButton));
		updateBtn.click();
	}

	// Get the grand total price from the cart
	public double getGrandTotal() {
		WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(grandTotal));
		String totalText = totalElement.getText().replace("$", "").replace(",", "").trim();
		return Double.parseDouble(totalText);
	}
}
