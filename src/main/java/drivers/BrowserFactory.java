package drivers;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BrowserFactory {
    private static final Logger logger = LogManager.getLogger(BrowserFactory.class);
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getBrowser() {
        if (driverThread.get() == null) {
            String executionMode = ConfigReader.getExecutionMode();
            String browser = ConfigReader.getBrowser();
            boolean headless = ConfigReader.isHeadless();
            
            logger.info("Execution Mode: {}", executionMode);

            if ("grid".equalsIgnoreCase(executionMode)) {
                logger.info("Running tests on Selenium Grid...");
                driverThread.set(getRemoteDriver(browser, headless));
            } else {
                logger.info("Running tests locally...");
                driverThread.set(getLocalDriver(browser, headless));
            }

            driverThread.get().manage().window().maximize();
            driverThread.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        }
        return driverThread.get();
    }

    private static WebDriver getRemoteDriver(String browser, boolean headless) {
        String gridUrl = ConfigReader.getGridUrl();
        logger.info("Connecting to Selenium Grid at: {}", gridUrl);
        try {
            switch (browser) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("-headless");
                    return new RemoteWebDriver(new URL(gridUrl), firefoxOptions);

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) edgeOptions.addArguments("--headless");
                    return new RemoteWebDriver(new URL(gridUrl), edgeOptions);

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) chromeOptions.addArguments("--headless");
                    return new RemoteWebDriver(new URL(gridUrl), chromeOptions);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }
    }

    private static WebDriver getLocalDriver(String browser, boolean headless) {
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                return new org.openqa.selenium.firefox.FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");
                return new org.openqa.selenium.edge.EdgeDriver(edgeOptions);

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless");
                return new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
        }
    }

    public static void closeBrowser() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
            logger.info("Browser closed successfully");
        }
    }
}
