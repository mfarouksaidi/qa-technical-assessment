package com.qa.runners;

import org.junit.platform.suite.api.*;

/**
 * Smoke Test Runner - Runs only smoke tests
 * Filters tests using @smoke tag
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/reports/smoke-cucumber-reports.html, json:target/reports/smoke-cucumber.json")
@ConfigurationParameter(key = "cucumber.glue", value = "com.qa.steps,com.qa.hooks")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@smoke")
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "false")
public class TestRunnerSmoke {
    // This class will be empty - configuration is in annotations
}
