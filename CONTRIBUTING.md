# Contributing Guide

Thank you for your interest in contributing to this QA Automation Framework!

## Getting Started

### Prerequisites
- Java JDK 11+
- Maven 3.6+
- Git
- IDE (IntelliJ IDEA recommended)

### Setup Development Environment

1. **Fork and Clone**
   ```bash
   git clone https://github.com/YOUR_USERNAME/QA-techmical-assessment.git
   cd QA-techmical-assessment
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Import into IDE**
   - IntelliJ IDEA: File → Open → Select pom.xml
   - Eclipse: File → Import → Existing Maven Projects

## Project Structure

```
src/test/java/com/qa/
├── api/              # API testing framework
├── common/           # Shared utilities
├── hooks/            # Cucumber hooks
├── runners/          # Test runners
├── steps/            # Step definitions
└── ui/               # UI testing framework

src/test/resources/
├── features/         # Gherkin feature files
├── schemas/          # JSON schemas
├── testdata/         # Test data files
└── config.properties # Configuration
```

## Coding Standards

### Java Code Style

1. **Naming Conventions**
   - Classes: `PascalCase` (e.g., `CarsShowroomPage`)
   - Methods: `camelCase` (e.g., `getCarCount()`)
   - Variables: `camelCase` (e.g., `carCount`)
   - Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_TIMEOUT`)

2. **Formatting**
   - Indentation: 4 spaces (no tabs)
   - Line length: Max 120 characters
   - Braces: Opening brace on same line

3. **Documentation**
   - All public classes must have JavaDoc
   - Complex methods should have JavaDoc
   - Use inline comments for complex logic

### Example
```java
/**
 * Page Object for Cars Showroom page
 * Provides methods to interact with car listings
 */
public class CarsShowroomPage extends BasePage {
    
    /**
     * Gets the total count of cars displayed on the page
     * @return Number of car cards found
     */
    public int getCarCount() {
        return carCards.size();
    }
}
```

## Writing Tests

### Feature Files

1. **Structure**
   ```gherkin
   @tag
   Feature: Feature Name
     Brief description
   
     Background:
       Common setup steps
   
     @scenario-tag
     Scenario: Scenario Name
       Given precondition
       When action
       Then assertion
   ```

2. **Best Practices**
   - Use descriptive scenario names
   - One logical test per scenario
   - Use Background for common setup
   - Tag appropriately (@api, @ui, @smoke, etc.)
   - Keep scenarios independent

### Step Definitions

1. **Pattern Matching**
   ```java
   @When("I search for {string}")
   public void iSearchFor(String searchTerm) {
       carsShowroomPage.searchForCar(searchTerm);
   }
   ```

2. **Best Practices**
   - Reuse existing steps when possible
   - Keep step methods focused and simple
   - Use descriptive method names
   - Add logging for debugging
   - Use AssertJ for assertions

### Page Objects

1. **Structure**
   ```java
   public class MyPage extends BasePage {
       @FindBy(css = ".element")
       private WebElement element;
       
       public MyPage(WebDriver driver) {
           super(driver);
       }
       
       public void clickElement() {
           click(element);
       }
   }
   ```

2. **Best Practices**
   - Extend `BasePage` for common functionality
   - Use `@FindBy` for element location
   - Return page objects for method chaining
   - No assertions in page objects
   - Hide WebDriver complexity

### API Clients

1. **Structure**
   ```java
   public class MyApiClient extends ApiClient {
       private static final String ENDPOINT = "/resource";
       
       public Response getResource(int id) {
           return get(ENDPOINT + "/" + id);
       }
   }
   ```

2. **Best Practices**
   - Extend `ApiClient` base class
   - Use constants for endpoints
   - Return `Response` objects
   - Add logging
   - Handle common error scenarios

## Testing Your Changes

### Run Tests Locally

```bash
# All tests
mvn clean test

# Specific test suite
mvn clean test -Dtest=TestRunnerApi

# With tags
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Verify Code Quality

```bash
# Compile
mvn clean compile

# Run specific test class
mvn test -Dtest=TestRunnerSmoke
```

## Pull Request Process

### Before Submitting

1. ✅ Code compiles without errors
2. ✅ All tests pass locally
3. ✅ New tests added for new features
4. ✅ Code follows project conventions
5. ✅ Documentation updated (if needed)
6. ✅ No sensitive data committed

### PR Guidelines

1. **Branch Naming**
   - Feature: `feature/description`
   - Bug fix: `bugfix/description`
   - Hotfix: `hotfix/description`

2. **Commit Messages**
   ```
   <type>: <subject>
   
   <body>
   
   <footer>
   ```
   
   Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
   
   Example:
   ```
   feat: Add pagination support to Cars Showroom page
   
   - Added pagination elements to page object
   - Implemented navigation methods
   - Added test scenarios for pagination
   
   Closes #123
   ```

3. **PR Description Template**
   ```markdown
   ## Description
   Brief description of changes
   
   ## Type of Change
   - [ ] Bug fix
   - [ ] New feature
   - [ ] Breaking change
   - [ ] Documentation update
   
   ## Testing
   - [ ] All tests pass
   - [ ] New tests added
   
   ## Checklist
   - [ ] Code follows style guidelines
   - [ ] Self-review completed
   - [ ] Documentation updated
   ```

## Adding New Features

### New UI Page

1. Create page object in `src/test/java/com/qa/ui/pages/`
2. Extend `BasePage`
3. Add feature file in `src/test/resources/features/ui/`
4. Create/update step definitions
5. Add tests

### New API Endpoint

1. Create/update API client in `src/test/java/com/qa/api/`
2. Create model classes if needed
3. Add feature file in `src/test/resources/features/api/`
4. Create/update step definitions
5. Add tests

### New Test Runner

1. Create runner in `src/test/java/com/qa/runners/`
2. Configure Cucumber options
3. Add tag filtering
4. Update README with usage

## Common Issues

### WebDriver Issues
```bash
# Clear cached drivers
rm -rf ~/.m2/repository/webdriver
mvn clean test
```

### Dependency Issues
```bash
# Force update
mvn clean install -U
```

### IDE Issues
```bash
# Reimport Maven project
# IntelliJ: Right-click pom.xml → Maven → Reload Project
```

## Resources

- [Cucumber Documentation](https://cucumber.io/docs/)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [REST Assured Documentation](https://rest-assured.io/)
- [AssertJ Documentation](https://assertj.github.io/doc/)

## Questions?

Feel free to:
- Open an issue for bugs
- Start a discussion for questions
- Submit a PR for improvements

---

**Happy Testing! 🚀**
