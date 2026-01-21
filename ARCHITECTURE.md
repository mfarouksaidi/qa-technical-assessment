# Framework Architecture

## Overview

This BDD automation framework follows a layered architecture approach with clear separation of concerns, making it maintainable, scalable, and easy to extend.

## Architecture Layers

```
┌─────────────────────────────────────────────────────────┐
│                    Feature Files                         │
│              (Business Readable Tests)                   │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                  Step Definitions                        │
│            (Glue Code / Test Logic)                      │
└─────────────────────────────────────────────────────────┘
                          ↓
┌──────────────────────┬──────────────────────────────────┐
│    Page Objects      │        API Clients               │
│   (UI Abstraction)   │    (API Abstraction)             │
└──────────────────────┴──────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              Common Utilities & Helpers                  │
│   (Config, Drivers, Context, Wait Helpers)              │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              External Dependencies                       │
│   (Selenium, REST Assured, Cucumber, JUnit)             │
└─────────────────────────────────────────────────────────┘
```

## Component Details

### 1. Feature Files Layer
- **Location**: `src/test/resources/features/`
- **Purpose**: Business-readable test scenarios written in Gherkin
- **Technology**: Cucumber BDD
- **Organization**:
  - `api/` - API test scenarios
  - `ui/` - UI test scenarios

### 2. Step Definitions Layer
- **Location**: `src/test/java/com/qa/steps/`
- **Purpose**: Implementation of Gherkin steps
- **Components**:
  - `ApiSteps.java` - API test step implementations
  - `UiSteps.java` - UI test step implementations
- **Pattern**: Uses Dependency Injection (PicoContainer) for sharing context

### 3. Page Objects Layer (UI)
- **Location**: `src/test/java/com/qa/ui/pages/`
- **Purpose**: Encapsulate UI elements and interactions
- **Pattern**: Page Object Model (POM)
- **Components**:
  - `BasePage.java` - Common page methods
  - `CarsShowroomPage.java` - Cars showroom specific methods
- **Benefits**:
  - Reduced code duplication
  - Easy maintenance
  - Separation of test logic from UI structure

### 4. API Clients Layer
- **Location**: `src/test/java/com/qa/api/`
- **Purpose**: Encapsulate API interactions
- **Components**:
  - `ApiClient.java` - Base API client with common methods
  - `CarsApiClient.java` - Cars-specific API operations
  - `models/` - POJO models for API requests/responses
- **Technology**: REST Assured
- **Benefits**:
  - Reusable API methods
  - Clean abstraction over HTTP operations
  - Type-safe request/response handling

### 5. Common Utilities Layer
- **Location**: `src/test/java/com/qa/common/`
- **Purpose**: Shared utilities and helpers
- **Components**:
  - `ConfigManager.java` - Configuration management (Singleton)
  - `DriverManager.java` - WebDriver lifecycle management (Factory)
  - `TestContext.java` - Shared test state (DI)
  - `WaitHelper.java` - Explicit wait utilities
  - `ScreenshotUtil.java` - Screenshot capture utilities

### 6. Hooks & Runners
- **Hooks** (`src/test/java/com/qa/hooks/`):
  - `TestHooks.java` - Before/After scenario setup/teardown
  - Screenshot on failure
  - Driver cleanup
  - Logging
  
- **Runners** (`src/test/java/com/qa/runners/`):
  - `TestRunnerAll.java` - Execute all tests
  - `TestRunnerApi.java` - Execute API tests only
  - `TestRunnerUi.java` - Execute UI tests only
  - `TestRunnerSmoke.java` - Execute smoke tests only

## Design Patterns

### 1. Page Object Model (POM)
**Purpose**: Separate page structure from test logic

```java
public class CarsShowroomPage extends BasePage {
    @FindBy(css = ".car-card")
    private List<WebElement> carCards;
    
    public int getCarCount() {
        return carCards.size();
    }
}
```

**Benefits**:
- UI changes only affect page objects, not tests
- Reusable page methods
- Improved readability

### 2. Singleton Pattern
**Purpose**: Single instance of configuration throughout execution

```java
public class ConfigManager {
    private static ConfigManager instance;
    
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
}
```

**Benefits**:
- Centralized configuration
- Memory efficient
- Thread-safe

### 3. Factory Pattern
**Purpose**: Create browser instances dynamically

```java
public class DriverManager {
    private static WebDriver createDriver() {
        switch (browser) {
            case "firefox": return new FirefoxDriver();
            case "chrome": return new ChromeDriver();
            default: return new ChromeDriver();
        }
    }
}
```

**Benefits**:
- Easy to add new browsers
- Centralized driver creation
- Configuration-driven

### 4. Dependency Injection (DI)
**Purpose**: Share state between step definitions

```java
public class ApiSteps {
    private final TestContext context;
    
    public ApiSteps(TestContext context) {
        this.context = context;
    }
}
```

**Benefits**:
- Loose coupling
- Easy to test
- Automatic lifecycle management via PicoContainer

## Data Flow

### UI Test Flow
```
Feature File → UiSteps → CarsShowroomPage → WebDriver → Browser
     ↓              ↓             ↓
TestContext ← DriverManager ← ConfigManager
```

### API Test Flow
```
Feature File → ApiSteps → CarsApiClient → REST Assured → API
     ↓              ↓            ↓
TestContext ← ApiResponse ← ConfigManager
```

## Configuration Management

### Hierarchy
1. **JVM System Properties** (Highest priority)
   - `-Dbrowser=chrome`
   - `-Dheadless=true`

2. **config.properties** (Default values)
   - `browser=chrome`
   - `base.url=https://...`

3. **Hardcoded Defaults** (Fallback)

### Usage
```java
String browser = ConfigManager.getInstance().getBrowser();
// Checks: System Property → config.properties → Default
```

## Test Execution Flow

```
1. JUnit/Maven triggers Test Runner
         ↓
2. Cucumber discovers Feature Files
         ↓
3. @BeforeAll Hook - Setup directories
         ↓
4. For each scenario:
   a. @Before Hook - Initialize context
   b. Execute Gherkin steps
   c. Step Definitions call Page Objects/API Clients
   d. Assertions validate results
   e. @After Hook - Cleanup, screenshot on failure
         ↓
5. @AfterAll Hook - Final cleanup
         ↓
6. Generate Reports
```

## Reporting Architecture

### Report Types
1. **Cucumber HTML Reports**
   - Location: `target/reports/cucumber-reports.html`
   - Contains: Scenario results, steps, screenshots

2. **JSON Reports**
   - Location: `target/reports/cucumber.json`
   - Purpose: Integration with CI/CD tools

3. **JUnit XML Reports**
   - Location: `target/surefire-reports/`
   - Purpose: CI/CD test result publishing

4. **Logs**
   - Location: `target/logs/test-execution.log`
   - Format: Timestamped, thread-aware
   - Level: Configurable (INFO, DEBUG, ERROR)

### Screenshot Management
- **Trigger**: Test failure
- **Format**: PNG with timestamp
- **Storage**: `target/screenshots/`
- **Attachment**: Embedded in Cucumber reports

## CI/CD Architecture

### GitHub Actions Workflow

```
Trigger (Push/PR/Schedule/Manual)
          ↓
    Checkout Code
          ↓
    Setup Java 11
          ↓
    Install Browser (Chrome)
          ↓
    Execute Tests (Parallel)
    ├── API Tests
    ├── UI Tests (Headless)
    └── Smoke Tests
          ↓
    Upload Artifacts
    ├── HTML Reports
    ├── Screenshots
    └── Logs
          ↓
    Publish Test Results
```

### Parallel Execution
- API and UI tests run in parallel jobs
- Faster feedback
- Isolated environments
- Independent failure handling

## Scalability Considerations

### Horizontal Scalability
- **Selenium Grid**: Distribute UI tests across nodes
- **Parallel Execution**: Maven Surefire parallel configuration
- **Cloud Execution**: BrowserStack, Sauce Labs integration ready

### Vertical Scalability
- **Modular Design**: Easy to add new features/pages
- **Tag-based Execution**: Run subsets of tests
- **Data-driven Tests**: Scenario Outlines with Examples
- **Reusable Components**: Base classes, utilities

## Error Handling Strategy

### Levels
1. **Page Object Level**: Element not found exceptions
2. **Step Definition Level**: Assertion failures
3. **Hook Level**: Screenshot capture, cleanup
4. **Runner Level**: Test suite execution

### Logging
- **DEBUG**: Detailed step information
- **INFO**: Test progress, key actions
- **ERROR**: Failures, exceptions with stack traces

## Security Considerations

1. **Credentials**: Never hardcoded, use environment variables
2. **API Keys**: Externalized in secure config
3. **Screenshots**: May contain sensitive data, handle carefully
4. **Logs**: Sanitize sensitive information

## Performance Optimization

1. **WebDriverManager**: Cached driver binaries
2. **Implicit Waits**: Minimal, use explicit waits
3. **Page Load Strategy**: NORMAL (can be EAGER/NONE)
4. **Headless Mode**: CI/CD executions
5. **Connection Pooling**: REST Assured auto-manages

## Future Enhancements

1. **Docker Integration**: Containerized test execution
2. **Database Validation**: Data layer testing
3. **Visual Regression**: Screenshot comparison
4. **Performance Testing**: JMeter/Gatling integration
5. **Mobile Testing**: Appium integration
6. **Accessibility Testing**: aXe integration
7. **Contract Testing**: Pact integration

## Best Practices Implemented

✅ Single Responsibility Principle
✅ DRY (Don't Repeat Yourself)
✅ Separation of Concerns
✅ Consistent Naming Conventions
✅ Comprehensive Logging
✅ Error Recovery Mechanisms
✅ Clean Code Principles
✅ Documentation & Comments
✅ Version Control
✅ CI/CD Integration
