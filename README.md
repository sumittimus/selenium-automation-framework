

#HOW TO RUN THIS FRAMEWORK LOCALLY (WITHOUT SELENIUM GRID)
Follow these steps to run the automation framework locally (without using Selenium Grid).

###Step 1: Prerequisites
Ensure the following tools are installed and properly configured:
Java 17:
Install from Oracle Java Downloads.
Verify installation: java -version

Maven:
Install from Maven Official Site.
Verify installation: mvn -version
Browser Drivers: The framework uses WebDriverManager, so no manual driver download is required.

###Step 2: Build the Project
mvn clean install

###Step 3 : Run The Tests Locally
##### Option 1: Run with Maven Command
To execute tests using Chrome browser: mvn test -Dbrowser=chrome -DexecutionMode=local
To execute tests using Firefox: mvn test -Dbrowser=firefox -DexecutionMode=local

#### Option 2: Run with TestNG
Execute all tests using the testng.xml file: mvn clean test -DsuiteXmlFile=testng.xml

#### Option 3: Run Specific Tests
mvn test -Dcucumber.features="src/test/resources/features/UIFlow.feature"

#### Option 4: Run Tests Sequentially
mvn test -DexecutionMode=local -Dparallel=none

###Step 4 : Analyzing Test Reports
After execution, reports will be available in the target directory: 
target/cucumber-reports/cucumber-html-report.html
target/surefire-reports/emailable-report.html

###Step 5 : Clean Up
mvn clean

##################################################################################################################

