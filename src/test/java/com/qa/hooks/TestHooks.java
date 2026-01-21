package com.qa.hooks;

import com.qa.common.ConfigManager;
import com.qa.common.DriverManager;
import com.qa.common.TestContext;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cucumber Hooks for test lifecycle management
 * Handles setup and teardown operations for scenarios
 */
public class TestHooks {
    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
    private final TestContext context;

    public TestHooks(TestContext context) {
        this.context = context;
    }

    @BeforeAll
    public static void beforeAll() {
        logger.info("==========================================");
        logger.info("Starting Test Execution");
        logger.info("==========================================");
        createDirectories();
    }

    @Before
    public void before(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
    }

    @Before("@ui")
    public void beforeUiScenario(Scenario scenario) {
        logger.info("Initializing UI test for scenario: {}", scenario.getName());
        // Driver will be initialized lazily in step definitions
    }

    @Before("@api")
    public void beforeApiScenario(Scenario scenario) {
        logger.info("Initializing API test for scenario: {}", scenario.getName());
        // API client setup is handled in step definitions
    }

    @After
    public void after(Scenario scenario) {
        logger.info("Scenario '{}' finished with status: {}", 
                    scenario.getName(), scenario.getStatus());

        if (scenario.isFailed()) {
            handleFailure(scenario);
        }

        // Clear context after each scenario
        context.clearContext();
    }

    @After("@ui")
    public void afterUiScenario(Scenario scenario) {
        WebDriver driver = context.getDriver();
        if (driver != null) {
            logger.info("Closing browser for scenario: {}", scenario.getName());
            DriverManager.quitDriver();
            context.setDriver(null);
        }
    }

    @AfterAll
    public static void afterAll() {
        logger.info("==========================================");
        logger.info("Test Execution Completed");
        logger.info("==========================================");
    }

    private void handleFailure(Scenario scenario) {
        logger.error("Scenario failed: {}", scenario.getName());

        WebDriver driver = context.getDriver();
        if (driver != null && ConfigManager.getInstance().shouldTakeScreenshotOnFailure()) {
            takeScreenshot(scenario, driver);
        }

        // Log additional failure information
        if (context.getApiResponse() != null) {
            logger.error("API Response Status: {}", 
                        context.getApiResponse().getStatusCode());
            logger.error("API Response Body: {}", 
                        context.getApiResponse().getBody().asString());
        }
    }

    private void takeScreenshot(Scenario scenario, WebDriver driver) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");

            // Also save to file
            String screenshotPath = ConfigManager.getInstance().getScreenshotPath();
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_") 
                    + "_" + timestamp + ".png";
            Path path = Paths.get(screenshotPath, fileName);

            Files.write(path, screenshot);
            logger.info("Screenshot saved: {}", path);
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
        }
    }

    private static void createDirectories() {
        String[] directories = {
            "target/screenshots",
            "target/logs",
            "target/reports"
        };

        for (String dir : directories) {
            File directory = new File(dir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    logger.info("Created directory: {}", dir);
                }
            }
        }
    }
}
