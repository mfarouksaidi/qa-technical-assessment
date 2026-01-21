# QA Technical Assessment - BDD Automation Framework

[![CI - Automated Testing Pipeline](https://github.com/YOUR_USERNAME/QA-techmical-assessment/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/QA-techmical-assessment/actions/workflows/ci.yml)

A comprehensive BDD (Behavior-Driven Development) automation framework using Java, Cucumber, Selenium, and REST Assured for testing web applications and APIs.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [System Under Test](#system-under-test)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Test Reporting](#test-reporting)
- [CI/CD Integration](#cicd-integration)
- [Design Patterns & Best Practices](#design-patterns--best-practices)
- [Contributing](#contributing)

## 🎯 Project Overview

This project demonstrates a professional-grade test automation framework that covers:

- **API Testing**: RESTful API testing using REST Assured
- **UI Testing**: Web UI testing using Selenium WebDriver
- **BDD Framework**: Cucumber for behavior-driven testing
- **CI/CD**: GitHub Actions pipeline for continuous testing
- **Design Patterns**: Page Object Model, Singleton, Factory patterns
- **Shift-Left Testing**: Early integration of testing in development lifecycle

## 🌐 System Under Test

### Web UI
- **URL**: https://practice.expandtesting.com/cars
- **Domain**: Cars Showroom application

### Public API
- **Base URL**: https://practice.expandtesting.com/api
- **Documentation**: https://practice.expandtesting.com/api/api-docs/
- **Domain**: Cars API endpoints

## 🛠 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 11 |
| Build Tool | Maven | 3.x |
| BDD Framework | Cucumber | 7.15.0 |
| UI Automation | Selenium WebDriver | 4.16.1 |
| API Testing | REST Assured | 5.4.0 |
| Test Framework | JUnit | 5.10.1 |
| Driver Management | WebDriverManager | 5.6.3 |
| Logging | SLF4J + Logback | 2.0.9 |
| Assertions | AssertJ | 3.25.1 |
| JSON Processing | Jackson | 2.16.1 |
| CI/CD | GitHub Actions | - |

## 📁 Project Structure

```
QA-techmical-assessment/
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions CI/CD pipeline
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── qa/
│       │           ├── api/           # API testing framework
│       │           │   ├── ApiClient.java
│       │           │   ├── CarsApiClient.java
│       │           │   └── models/
│       │           │       └── Car.java
│       │           ├── common/        # Common utilities
│       │           │   ├── ConfigManager.java
│       │           │   ├── DriverManager.java
│       │           │   └── TestContext.java
│       │           ├── hooks/         # Cucumber hooks
│       │           │   └── TestHooks.java
│       │           ├── runners/       # Test runners
│       │           │   ├── TestRunnerAll.java
│       │           │   ├── TestRunnerApi.java
│       │           │   ├── TestRunnerUi.java
│       │           │   └── TestRunnerSmoke.java
│       │           ├── steps/         # Step definitions
│       │           │   ├── ApiSteps.java
│       │           │   └── UiSteps.java
│       │           └── ui/            # UI testing framework
│       │               └── pages/
│       │                   ├── BasePage.java
│       │                   └── CarsShowroomPage.java
│       └── resources/
│           ├── features/              # Cucumber feature files
│           │   ├── api/
│           │   │   └── cars_api.feature
│           │   └── ui/
│           │       └── cars_showroom.feature
│           ├── config.properties      # Configuration file
│           └── logback.xml            # Logging configuration
├── target/
│   ├── reports/                       # Test execution reports
│   ├── screenshots/                   # Failure screenshots
│   └── logs/                          # Execution logs
├── pom.xml                            # Maven dependencies
└── README.md                          # This file
```

## 🚀 Setup Instructions

### Prerequisites

- **Java JDK 11** or higher installed
- **Maven 3.6+** installed
- **Git** installed
- **Chrome/Firefox** browser installed
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/QA-techmical-assessment.git
   cd QA-techmical-assessment
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Verify setup**
   ```bash
   mvn clean compile
   ```

4. **Configure properties** (Optional)
   
   Edit `src/test/resources/config.properties` to customize:
   - Browser type (chrome, firefox, edge)
   - Headless mode
   - Timeouts
   - Base URLs

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run API Tests Only
```bash
mvn clean test -Dtest=TestRunnerApi
```

### Run UI Tests Only
```bash
mvn clean test -Dtest=TestRunnerUi
```

### Run Smoke Tests Only
```bash
mvn clean test -Dtest=TestRunnerSmoke
```

### Run Tests in Headless Mode
```bash
mvn clean test -Dheadless=true
```

### Run Tests with Specific Browser
```bash
mvn clean test -Dbrowser=firefox
```

### Run Tests with Specific Tags
```bash
mvn clean test -Dcucumber.filter.tags="@api and @smoke"
```

### Run Tests from IDE

1. Right-click on any test runner class (e.g., `TestRunnerAll.java`)
2. Select "Run As > JUnit Test"

## 📊 Test Reporting

Test reports are generated automatically after execution:

### Cucumber HTML Reports
- **Location**: `target/reports/cucumber-reports.html`
- **View**: Open in browser for detailed test results

### JSON Reports
- **Location**: `target/reports/cucumber.json`
- **Use**: For integration with reporting tools

### Screenshots (on failure)
- **Location**: `target/screenshots/`
- **Format**: PNG images with timestamp

### Execution Logs
- **Location**: `target/logs/test-execution.log`
- **Level**: Configurable in `logback.xml`

## 🔄 CI/CD Integration

### GitHub Actions Pipeline

The project includes a comprehensive CI/CD pipeline (`.github/workflows/ci.yml`) that:

- ✅ Runs on every push to `main` or `develop` branches
- ✅ Runs on pull requests
- ✅ Scheduled daily execution at 2 AM UTC
- ✅ Manual trigger with test suite selection
- ✅ Parallel execution of API and UI tests
- ✅ Automated test result publishing
- ✅ Artifact upload (reports, screenshots, logs)
- ✅ Code quality checks

### Pipeline Jobs

1. **API Tests**: Executes all API test scenarios
2. **UI Tests**: Executes all UI test scenarios in headless mode
3. **Smoke Tests**: Quick verification tests
4. **All Tests**: Complete test suite execution
5. **Code Quality**: Compilation and validation checks

### Triggering Pipeline

**Automatic Triggers:**
- Push to main/develop branches
- Pull request creation
- Daily schedule

**Manual Trigger:**
```bash
# Via GitHub UI
Actions → CI - Automated Testing Pipeline → Run workflow
```

## 🏗 Design Patterns & Best Practices

### Design Patterns Implemented

1. **Page Object Model (POM)**
   - Encapsulates page elements and interactions
   - Improves maintainability and reduces duplication
   - Location: `src/test/java/com/qa/ui/pages/`

2. **Singleton Pattern**
   - ConfigManager for centralized configuration
   - Ensures single instance throughout test execution

3. **Factory Pattern**
   - DriverManager creates browser instances
   - Supports multiple browsers dynamically

4. **Dependency Injection**
   - PicoContainer for sharing context between steps
   - TestContext for state management

### Best Practices

✅ **Separation of Concerns**: Clear separation between tests, page objects, and utilities

✅ **DRY Principle**: Reusable methods and base classes

✅ **Externalized Configuration**: All settings in properties file

✅ **Logging**: Comprehensive logging for debugging

✅ **Error Handling**: Proper exception handling and reporting

✅ **Wait Strategies**: Explicit and implicit waits for stability

✅ **Screenshots on Failure**: Automatic capture for debugging

✅ **Clean Code**: Meaningful names, comments, and documentation

### BDD Approach

- **Gherkin Syntax**: Human-readable test scenarios
- **Living Documentation**: Feature files serve as documentation
- **Collaboration**: Business-readable test cases
- **Traceability**: Clear mapping between requirements and tests

## 📝 Test Coverage

### API Tests
- ✅ GET all cars
- ✅ GET specific car by ID
- ✅ POST create new car
- ✅ PUT update existing car
- ✅ DELETE remove car
- ✅ Negative scenarios (404, 400)
- ✅ Schema validation
- ✅ Performance checks

### UI Tests
- ✅ Page load verification
- ✅ Car listing display
- ✅ Search functionality
- ✅ Filter operations (make, year, model)
- ✅ Multiple filter combinations
- ✅ Navigation tests
- ✅ Responsive design validation
- ✅ Sorting capabilities
- ✅ Pagination

## 🔧 Configuration

### config.properties

```properties
# Application URLs
base.url=https://practice.expandtesting.com
api.base.url=https://practice.expandtesting.com/api

# Browser Configuration
browser=chrome          # chrome, firefox, edge
headless=false          # true for CI/CD
browser.timeout=30
implicit.wait=10
explicit.wait=20

# API Configuration
api.timeout=30000
api.content.type=application/json

# Screenshots
screenshot.on.failure=true
screenshot.path=target/screenshots
```

### Override via Command Line

```bash
mvn test -Dbrowser=firefox -Dheadless=true -Dapi.timeout=60000
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is created for QA assessment purposes.

## 👤 Author

**Senior QA Automation Engineer Candidate**

## 📞 Support

For questions or issues, please open an issue in the GitHub repository.

---

## 🎓 Key Highlights for Presentation

### Architecture Decisions

1. **Why Cucumber BDD?**
   - Enables collaboration between technical and non-technical stakeholders
   - Living documentation that stays in sync with code
   - Supports shift-left testing approach

2. **Why REST Assured for API Testing?**
   - Fluent and readable API
   - Built-in JSON/XML parsing
   - Comprehensive assertion library
   - Easy schema validation

3. **Why Selenium WebDriver?**
   - Industry standard for UI automation
   - Cross-browser compatibility
   - Strong community support
   - Integrates well with Cucumber

4. **Why Page Object Model?**
   - Improves code maintainability
   - Reduces code duplication
   - Makes tests more readable
   - Easier to update when UI changes

5. **Why GitHub Actions?**
   - Native GitHub integration
   - Free for public repositories
   - Easy to configure
   - Supports parallel execution
   - Artifact management built-in

### Shift-Left Testing Implementation

- Early API testing in development cycle
- Automated smoke tests on every commit
- Fast feedback via CI/CD pipeline
- Schema validation to catch breaking changes
- Performance testing integrated in pipeline

### Scalability & Extensibility

- Easy to add new test scenarios (just add feature files)
- Support for multiple browsers and environments
- Configurable test execution (tags, suites)
- Parallel execution capability
- Cloud execution ready (Selenium Grid, BrowserStack)

---

**Happy Testing! 🚀**
