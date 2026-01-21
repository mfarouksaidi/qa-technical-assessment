package com.qa.runners;

import org.junit.platform.suite.api.*;

/**
 * Main Test Runner - Runs all tests (API + UI)
 * Uses JUnit Platform Suite with Cucumber integration
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/reports/cucumber-reports.html, json:target/reports/cucumber.json")
@ConfigurationParameter(key = "cucumber.glue", value = "com.qa.steps,com.qa.hooks")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features")
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "false")
public class TestRunnerAll {
    // This class will be empty - configuration is in annotations
}
