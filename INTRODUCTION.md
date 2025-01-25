#High-Level Features of the Selenium Automation Framework

###PROJECT LOCATION : https://github.com/sumittimus/selenium-automation-framework/tree/feature_branch

The Selenium automation framework is designed to answer the coding challenge provided for IAG 2nd round interview. 
This framework provide robust, scalable, and maintainable automated testing for web applications and APIs. 

Below are the key features and capabilities of the framework:

Cucumber BDD Integration
- Supports Behavior Driven Development (BDD) using Cucumber.

Parallel Execution via TestNG
- Test cases are executed in parallel to optimize runtime and improve efficiency.
- Ensures thread-safe WebDriver instances for parallel execution.

Cross-Browser Testing with Browser Factory
- Supports execution on multiple browsers: Chrome, Firefox, Edge.
- Uses WebDriverManager for automatic driver management.
- Allows execution on local machines and remote Selenium Grid.
- Configurable via config.properties for dynamic browser selection.

Execution Modes (Local & Remote/Docker Grid)
Tests can be executed:
- Via Actions in GitHub
- Locally: Using local browser instances.
- On Selenium Grid: Supporting distributed execution on Docker containers.
- Seamless switching between execution modes via configurations.

Page Object Model (POM) Design Pattern
- Implements a structured approach to UI automation with separate page classes.

Logging and Reporting Capabilities
- Log4j Integration
- TestNG Reports for test execution summary.
- Cucumber HTML Reports for BDD test execution tracking.

CI/CD Integration with GitHub Actions
- Automated test execution triggered on:
- Code push to feature branches.
- Pull requests for validation before merging.

Docker Support for Scalable Execution
- The framework can be executed inside Docker containers.