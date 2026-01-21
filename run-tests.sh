#!/bin/bash

# QA Automation Framework - Test Execution Script
# This script provides easy commands to run different test suites

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored messages
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to display usage
usage() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  all         Run all tests (API + UI)"
    echo "  api         Run API tests only"
    echo "  ui          Run UI tests only"
    echo "  smoke       Run smoke tests only"
    echo "  clean       Clean build artifacts"
    echo "  install     Install dependencies"
    echo "  report      Open test report in browser"
    echo "  help        Display this help message"
    echo ""
    echo "Examples:"
    echo "  $0 smoke              # Run smoke tests"
    echo "  $0 api                # Run API tests"
    echo "  HEADLESS=true $0 ui   # Run UI tests in headless mode"
    echo ""
}

# Function to run tests
run_tests() {
    local test_runner=$1
    local description=$2
    
    print_info "Starting $description..."
    
    if [ -n "$HEADLESS" ]; then
        print_warning "Running in headless mode"
        mvn clean test -Dtest=$test_runner -Dheadless=true
    else
        mvn clean test -Dtest=$test_runner
    fi
    
    if [ $? -eq 0 ]; then
        print_info "$description completed successfully!"
    else
        print_error "$description failed!"
        exit 1
    fi
}

# Function to open report
open_report() {
    local report_file="target/reports/cucumber-reports.html"
    
    if [ -f "$report_file" ]; then
        print_info "Opening test report..."
        
        # Detect OS and open accordingly
        if [[ "$OSTYPE" == "darwin"* ]]; then
            open "$report_file"
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            xdg-open "$report_file"
        elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "cygwin" ]]; then
            start "$report_file"
        else
            print_warning "Cannot auto-open report. Please open manually: $report_file"
        fi
    else
        print_error "Report not found. Please run tests first."
        exit 1
    fi
}

# Main script logic
case "$1" in
    all)
        run_tests "TestRunnerAll" "All Tests"
        ;;
    api)
        run_tests "TestRunnerApi" "API Tests"
        ;;
    ui)
        run_tests "TestRunnerUi" "UI Tests"
        ;;
    smoke)
        run_tests "TestRunnerSmoke" "Smoke Tests"
        ;;
    clean)
        print_info "Cleaning build artifacts..."
        mvn clean
        print_info "Clean completed!"
        ;;
    install)
        print_info "Installing dependencies..."
        mvn clean install -DskipTests
        print_info "Installation completed!"
        ;;
    report)
        open_report
        ;;
    help|--help|-h)
        usage
        ;;
    *)
        print_error "Invalid option: $1"
        echo ""
        usage
        exit 1
        ;;
esac

exit 0
