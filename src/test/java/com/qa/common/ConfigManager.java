package com.qa.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager for loading and accessing application properties
 * Implements Singleton pattern for centralized configuration management
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        String configPath = "src/test/resources/config.properties";

        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from {}", configPath);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", configPath, e);
            throw new RuntimeException("Configuration file not found: " + configPath);
        }
    }

    public String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public int getBrowserTimeout() {
        return Integer.parseInt(getProperty("browser.timeout", "30"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public boolean shouldTakeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots");
    }
}
