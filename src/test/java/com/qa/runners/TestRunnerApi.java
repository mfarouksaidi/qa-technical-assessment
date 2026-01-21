package com.qa.runners;

import org.junit.platform.suite.api.*;

/**
 * API Test Runner - Runs only API tests
 * Filters tests using @api tag
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/reports/api-cucumber-reports.html, json:target/reports/api-cucumber.json")
@ConfigurationParameter(key = "cucumber.glue", value = "com.qa.steps,com.qa.hooks")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features/api")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@api")
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "false")
public class TestRunnerApi {
    // This class will be empty - configuration is in annotations
}
