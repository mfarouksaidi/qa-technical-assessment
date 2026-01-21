# Quick Start Guide

Get up and running with the QA Automation Framework in 5 minutes!

## 🚀 Prerequisites

Ensure you have the following installed:
- ✅ Java JDK 11 or higher
- ✅ Maven 3.6 or higher
- ✅ Git
- ✅ Chrome or Firefox browser

### Verify Prerequisites

```bash
# Check Java version
java -version
# Expected: java version "11" or higher

# Check Maven version
mvn -version
# Expected: Apache Maven 3.6.x or higher

# Check Git
git --version
```

## 📦 Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/QA-techmical-assessment.git
cd QA-techmical-assessment
```

### Step 2: Install Dependencies

```bash
# Using Maven directly
mvn clean install -DskipTests

# OR using the provided script
./run-tests.sh install      # Mac/Linux
run-tests.bat install        # Windows
```

This will download all required dependencies (~5-10 minutes on first run).

## ▶️ Running Your First Test

### Option 1: Using Scripts (Recommended)

**Mac/Linux:**
```bash
# Run smoke tests (quickest)
./run-tests.sh smoke

# Run all tests
./run-tests.sh all

# Run API tests only
./run-tests.sh api

# Run UI tests only
./run-tests.sh ui

# Run UI tests in headless mode
HEADLESS=true ./run-tests.sh ui
```

**Windows:**
```cmd
REM Run smoke tests (quickest)
run-tests.bat smoke

REM Run all tests
run-tests.bat all

REM Run API tests only
run-tests.bat api

REM Run UI tests only
run-tests.bat ui

REM Run UI tests in headless mode
set HEADLESS=true & run-tests.bat ui
```

### Option 2: Using Maven Directly

```bash
# Run all tests
mvn clean test

# Run specific test suite
mvn clean test -Dtest=TestRunnerSmoke
mvn clean test -Dtest=TestRunnerApi
mvn clean test -Dtest=TestRunnerUi

# Run with custom properties
mvn clean test -Dbrowser=firefox -Dheadless=true

# Run specific tags
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Option 3: Using IDE

**IntelliJ IDEA:**
1. Navigate to `src/test/java/com/qa/runners/`
2. Right-click on `TestRunnerSmoke.java`
3. Select "Run 'TestRunnerSmoke'"

**Eclipse:**
1. Navigate to `src/test/java/com/qa/runners/`
2. Right-click on `TestRunnerSmoke.java`
3. Select "Run As → JUnit Test"

## 📊 Viewing Results

### View HTML Report

**Using Script:**
```bash
./run-tests.sh report        # Mac/Linux
run-tests.bat report         # Windows
```

**Manually:**
- Open `target/reports/cucumber-reports.html` in your browser

### View Console Output

Test results are printed to console in real-time with color-coded output:
- ✅ Green: Passed scenarios
- ❌ Red: Failed scenarios
- ⏭️ Yellow: Skipped scenarios

### View Logs

Detailed logs are available at:
- `target/logs/test-execution.log`

### Screenshots (on failure)

Failed test screenshots are saved to:
- `target/screenshots/`

## 🎯 Test Execution Patterns

### By Test Type

```bash
# API Tests Only (Fast - No browser needed)
mvn test -Dtest=TestRunnerApi

# UI Tests Only (Slower - Requires browser)
mvn test -Dtest=TestRunnerUi

# Smoke Tests (Critical path - Fast)
mvn test -Dtest=TestRunnerSmoke
```

### By Tags

```bash
# Run only @smoke tagged scenarios
mvn test -Dcucumber.filter.tags="@smoke"

# Run only @api tagged scenarios
mvn test -Dcucumber.filter.tags="@api"

# Run @api AND @smoke
mvn test -Dcucumber.filter.tags="@api and @smoke"

# Run @api OR @ui
mvn test -Dcucumber.filter.tags="@api or @ui"

# Exclude @wip (work in progress)
mvn test -Dcucumber.filter.tags="not @wip"
```

### By Browser

```bash
# Chrome (default)
mvn test -Dtest=TestRunnerUi -Dbrowser=chrome

# Firefox
mvn test -Dtest=TestRunnerUi -Dbrowser=firefox

# Edge
mvn test -Dtest=TestRunnerUi -Dbrowser=edge

# Headless Chrome
mvn test -Dtest=TestRunnerUi -Dheadless=true
```

## 📝 Writing Your First Test

### 1. Create a Feature File

Create `src/test/resources/features/api/my_test.feature`:

```gherkin
@api @mytest
Feature: My First Test
  
  @smoke
  Scenario: Verify API responds
    When I send a GET request to "/cars"
    Then the response status code should be 200
```

### 2. Run Your Test

```bash
mvn test -Dcucumber.filter.tags="@mytest"
```

That's it! The step definitions already exist and will be reused.

## 🔧 Configuration

### Basic Configuration

Edit `src/test/resources/config.properties`:

```properties
# Change browser
browser=chrome          # Options: chrome, firefox, edge

# Enable headless mode
headless=true

# Adjust timeouts (in seconds)
browser.timeout=30
implicit.wait=10
explicit.wait=20
```

### Environment-Specific Configuration

Override via command line:

```bash
mvn test \
  -Dbase.url=https://staging.example.com \
  -Dapi.base.url=https://staging-api.example.com \
  -Dheadless=true
```

## 🐛 Troubleshooting

### Issue: Tests fail with "WebDriver not found"

**Solution:**
```bash
# WebDriverManager handles this automatically
# If issue persists, clear Maven cache
rm -rf ~/.m2/repository/webdriver
mvn clean install
```

### Issue: Browser doesn't open (UI tests)

**Solution:**
```bash
# Check browser is installed
google-chrome --version    # Chrome
firefox --version          # Firefox

# Run in headless mode as workaround
mvn test -Dtest=TestRunnerUi -Dheadless=true
```

### Issue: Connection timeout in API tests

**Solution:**
```bash
# Increase timeout
mvn test -Dapi.timeout=60000

# Check if API is accessible
curl https://practice.expandtesting.com/api/cars
```

### Issue: Maven dependencies not downloading

**Solution:**
```bash
# Force update dependencies
mvn clean install -U

# Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

### Issue: Tests pass locally but fail in CI

**Solution:**
```bash
# Run in headless mode (CI environment)
mvn test -Dheadless=true

# Check CI logs for specific errors
# Ensure Chrome is installed in CI environment
```

## 📚 Next Steps

### Learn More
- 📖 Read [ARCHITECTURE.md](ARCHITECTURE.md) for framework design
- 🤝 Read [CONTRIBUTING.md](CONTRIBUTING.md) for development guidelines
- 📘 Read [README.md](README.md) for comprehensive documentation

### Explore Test Scenarios
- API Tests: `src/test/resources/features/api/`
- UI Tests: `src/test/resources/features/ui/`

### Understand the Code
- Page Objects: `src/test/java/com/qa/ui/pages/`
- API Clients: `src/test/java/com/qa/api/`
- Step Definitions: `src/test/java/com/qa/steps/`

### Try Advanced Features

```bash
# Parallel execution (edit pom.xml)
mvn test -Dparallel=methods -DthreadCount=4

# Generate different report formats
mvn test -Dcucumber.plugin="json:target/cucumber.json,html:target/cucumber.html"

# Run specific scenario by line number
mvn test -Dcucumber.features="src/test/resources/features/api/cars_api.feature:10"
```

## 🎓 Common Use Cases

### Daily Development Workflow

```bash
# 1. Pull latest changes
git pull origin main

# 2. Install dependencies (if updated)
mvn clean install -DskipTests

# 3. Run smoke tests before coding
./run-tests.sh smoke

# 4. Make your changes...

# 5. Run relevant tests
./run-tests.sh api    # If API changes
./run-tests.sh ui     # If UI changes

# 6. Run all tests before commit
./run-tests.sh all

# 7. View report
./run-tests.sh report
```

### Pre-Commit Checklist

```bash
# ✅ Code compiles
mvn clean compile

# ✅ Smoke tests pass
mvn test -Dtest=TestRunnerSmoke

# ✅ Relevant tests pass
mvn test -Dtest=TestRunnerApi  # or TestRunnerUi

# ✅ Code formatted
# ✅ No sensitive data

# Commit and push
git add .
git commit -m "feat: your feature description"
git push
```

### CI/CD Testing

```bash
# Simulate CI environment locally
mvn clean test -Dheadless=true -Dbrowser=chrome

# This is what runs in GitHub Actions
```

## 💡 Tips & Tricks

### Speed Up Test Execution

1. **Run API tests first** (no browser overhead)
   ```bash
   mvn test -Dtest=TestRunnerApi
   ```

2. **Use headless mode**
   ```bash
   mvn test -Dheadless=true
   ```

3. **Run specific tags**
   ```bash
   mvn test -Dcucumber.filter.tags="@smoke"
   ```

4. **Skip test compilation** (if no code changes)
   ```bash
   mvn surefire:test
   ```

### Debug Failing Tests

1. **Enable debug logging**
   - Edit `src/test/resources/logback.xml`
   - Change level from INFO to DEBUG

2. **Take manual screenshots**
   - Screenshots auto-captured on failure
   - Check `target/screenshots/`

3. **Review detailed logs**
   ```bash
   tail -f target/logs/test-execution.log
   ```

4. **Run single scenario**
   ```bash
   mvn test -Dcucumber.features="path/to/feature:LINE_NUMBER"
   ```

### Clean Workspace

```bash
# Clean all build artifacts
./run-tests.sh clean

# Deep clean (including IDE files)
mvn clean
rm -rf target/ .idea/ *.iml
```

## 🎉 Success!

You're now ready to use the QA Automation Framework!

**Happy Testing! 🚀**

---

## Quick Command Reference

| Task | Command |
|------|---------|
| Install | `mvn clean install -DskipTests` |
| Smoke Tests | `./run-tests.sh smoke` |
| API Tests | `./run-tests.sh api` |
| UI Tests | `./run-tests.sh ui` |
| All Tests | `./run-tests.sh all` |
| View Report | `./run-tests.sh report` |
| Clean | `./run-tests.sh clean` |
| Help | `./run-tests.sh help` |
