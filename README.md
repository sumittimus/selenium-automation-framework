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

##############################################################################################

# HOW TO RUN THE FRAMEWORK ON SELENIUM GRID USING DOCKER (On Local Machine)
#### STEP 1 : Install Docker on local machine
#### STEP 2 : Start Selenium Grid on Docker
docker-compose up -d
#### STEP 3 : Check docker status
docker ps
#### STEP 4 : Change the entry  in "config.properties"
executionMode=grid
#### STEP 5 : Run test via Maven
mvn test -Dbrowser=chrome -DexecutionMode=grid
#### STEP 6 : Monitor test on Grid UI (pwd : secret)
http://localhost:4444/ui
#### STEP 7 : Stop Grid services when done.
docker-compose down


################################################################################################

#HOW TO CHECKOUT THIS CODE FROM GITHUB
#### STEP 1 : Install Git.
#### STEP 2 : Navigate to the project workspace on your local machine
#### STEP 3 : Run
git clone -b feature_branch https://github.com/sumittimus/selenium-automation-framework.git
#### STEP 4 : Switch to branch if already cloned
git checkout feature_branch
#### STEP 5 : Pull latest updates from GitHub
git pull origin feature_branch
#### STEP 6 : Refer to above 2 sections on the method of execution







