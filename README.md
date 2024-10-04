
## RedBus Automated Testing

Automated testing project for the RedBus website. This project utilizes Selenium WebDriver, TestNG, Apache POI, Log4j, and ExtentReports to automate and validate various functionalities of the RedBus website.
## Running Tests

To run the tests, import the Maven project in Eclipse and run the Testng.xml file

## Project Structure

The project is structured as follows:

1. src/main/java: Contains the Driver Setup and Utils classes.
2. src/test/java: Contains the pages and test classes.
3. src/main/resources: Contains configuration properties for log4j.
4. src/test/resources: Contains configuration properties file
5. testData: Contains the excel file for test testData
6. Archive Test Result: Contains the Archived result for test Execution
7. Current Test Result: Contains the latest test result
8. log: Contains the logs for the test Execution
9. testng.xml: for running the tests


## Reporting

Test execution reports are generated using ExtentReports. These reports provide detailed information about test results and can be found in the test-output directory after running the tests.
## Implemented Features

1. Created at least 8 test cases across different pages.
2. Created a Page Object Model Framework using TestNG and Maven to implement these test cases.  
3. Test Data getting read from properties file.
4. Implemented proper waits.
5. The global configuration values like browser name, test site URL, global wait value etc. getting read from a properties file.
6. The test are runnable on following browsers Chrome, FF, IE.
7. Added proper assertions and taking screenshot when test case failed with the name same as test case and appended by a brief description of error in the screenshot file name.
8. Extent report created in the end of execution with appended error screenshots
9. Moving the Extent report and error screenshots in a directory named as Current Test Results and later on new execution moved the older results to a directory named as Archive Test result and store the latest result in Current Test Results.
10. Created multiple testing.xml file
11. Added logging in the framework using log4j
12. Implemented Retry for fail test cases
13. Created test case using Data provider. Excel Integration and reading data from Excel sheets (validSearch and invalidSearch in RedBusSearchBusTest class)