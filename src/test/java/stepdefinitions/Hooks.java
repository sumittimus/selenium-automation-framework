package stepdefinitions;

import drivers.BrowserFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Hooks {
	private WebDriver driver;

	private static final Logger logger = LogManager.getLogger(Hooks.class);

	@Before
	public void setUp(Scenario scenario) {
		logger.info("===== Starting Scenario: " + scenario.getName() + " =====");
	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			logger.error("Scenario '" + scenario.getName() + "' failed.");
		} else {
			logger.info("Scenario '" + scenario.getName() + "' passed.");
		}

		BrowserFactory.closeBrowser();
		logger.info("Browser closed successfully after scenario: " + scenario.getName());
	}
}
