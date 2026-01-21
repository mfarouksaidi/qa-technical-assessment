package com.qa.runners;

import org.junit.platform.suite.api.*;

/**
 * UI Test Runner - Runs only UI tests
 * Filters tests using @ui tag
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/reports/ui-cucumber-reports.html, json:target/reports/ui-cucumber.json")
@ConfigurationParameter(key = "cucumber.glue", value = "com.qa.steps,com.qa.hooks")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features/ui")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@ui")
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "false")
public class TestRunnerUi {
    // This class will be empty - configuration is in annotations
}

