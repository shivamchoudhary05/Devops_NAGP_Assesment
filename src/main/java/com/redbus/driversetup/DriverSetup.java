package com.redbus.driversetup;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.text.*;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {

    public static WebDriver driver;
    public static Properties envConfig;
    WebDriverWait wait;
    public static String BROWSER;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String excelpath;
    public static String sheetname;
    public static String sheetname1;
    public String report;
    public static String finaldir;
    public static final Logger logger = Logger.getLogger(DriverSetup.class);

    // Constructor to initialize the DriverSetup class
    public DriverSetup() {
        logger.info("Initializing DriverSetup");
        loadConfigProperties();
    }

    // Method to load configuration properties from file
    private void loadConfigProperties() {
        try (InputStream configFile = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config/config.properties")) {
            envConfig = new Properties();
            envConfig.load(configFile);
        } catch (FileNotFoundException e1) {
            logger.error("Config file not found", e1);
        } catch (IOException e) {
            logger.error("Error loading config file", e);
        }
    }

    // Method executed before each test method to select browser, set up WebDriver, and initialize it
    @BeforeMethod
    public void browserSelection(Method method) throws Exception {
        setBrowser();
        setWebDriver();
        initializeWebDriver();
    }

    // Method to set the browser type from configuration
    private void setBrowser() {
        BROWSER = envConfig.getProperty("browser");
        logger.info("Selecting browser: " + BROWSER);
    }

    // Method to set up WebDriver based on the selected browser type
    private void setWebDriver() {
        if (BROWSER.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (BROWSER.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (BROWSER.equals("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser type unsupported");
        }
    }

    // Method to initialize WebDriver settings and navigate to the base URL
    private void initializeWebDriver() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(envConfig.getProperty("baseUrl"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Method executed before the test suite to set up Extent Report
    @BeforeSuite
    public void setExtent() {
        setupExtentReport();
    }

    // Method to set up Extent Report
    private void setupExtentReport() {
        logger.info("Setting up Extent Report");
        moveCurrentResultsToArchive();
        String dateName = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
        String reportpath = System.getProperty("user.dir") + "/Current Test Results/Automation Report/";
        File filedir = new File(reportpath);
        filedir.mkdirs();
        report = reportpath + dateName + "_AutomationReport.html";
        htmlReporter = new ExtentHtmlReporter(report);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        setSystemInfo();
        configureReportSettings();
    }

    // Method to set system information for Extent Report
    private void setSystemInfo() {
        extent.setSystemInfo("User name", System.getProperty("user.name"));
        extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        extent.setSystemInfo("User Location", System.getProperty("user.country"));
        extent.setSystemInfo("OS name", System.getProperty("os.name"));
        extent.setSystemInfo("OS version", System.getProperty("os.version"));
    }

    // Method to configure settings for Extent Report
    private void configureReportSettings() {
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("RedBus Test Automation");
        htmlReporter.config().setReportName("Regression Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
    }

    // Method executed after the test suite to flush Extent Report
    @AfterSuite
    public void endReport() {
        logger.info("Flushing Extent Report");
        extent.flush();
    }

    // Method to capture screenshot on failure and tear down WebDriver after each test method
    @AfterMethod
    public void takeScreenShotOnFailureAndTearDown(ITestResult result) {
        logger.info("Taking screenshot on failure");
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                logFailedTest(result);
                captureAndAttachScreenshot(result);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                logPassedTest(result);
            } else {
                logSkippedTest(result);
            }
        } catch (Exception e) {
            logger.error("An error occurred while capturing and attaching screenshot: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    // Method to log failed test in Extent Report
    private void logFailedTest(ITestResult result) {
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
                ExtentColor.RED));
        test.fail(result.getThrowable());
    }

    // Method to capture and attach screenshot to Extent Report
    private void captureAndAttachScreenshot(ITestResult result) {
        try {
            String description = "Error";
            if (result.getThrowable() != null) {
                String errorMessage = result.getThrowable().getMessage();
                logger.info("Error message: " + errorMessage);
                if (errorMessage != null && (errorMessage.contains("XPath") || errorMessage.contains("NoSuchElementException")
                        || errorMessage.contains("element"))) {
                    description = errorMessage.contains("XPath") ? "XPath_Failure" : "NoSuchElementException";
                } else {
                    description = errorMessage.replaceAll("[^a-zA-Z0-9]", "_");
                }
            }
            String screenshotName = result.getName() + "_" + description;
            String screenshotPath = getScreenshot(driver, screenshotName);
            test.fail("Test case failed, see screenshot below:",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            logger.error("An error occurred while capturing and attaching screenshot: " + e.getMessage());
        }
    }

    // Method to capture screenshot
    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        logger.info("Taking screenshot: " + screenshotName);
        String dateName = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
        String finaldir = "";
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dir = System.getProperty("user.dir") + "/Current Test Results/Error Screenshots/";
            File filedir = new File(dir);
            filedir.mkdirs();
            finaldir = dir + screenshotName + "_" + dateName + ".png";
            File finalDestination = new File(finaldir);
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            logger.error("Error occurred while capturing screenshot: " + e.getMessage());
        }
        return finaldir;
    }

    // Method to log passed test in Extent Report
    private void logPassedTest(ITestResult result) {
        test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
    }

    // Method to log skipped test in Extent Report
    private void logSkippedTest(ITestResult result) {
        test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
        test.skip(result.getThrowable());
    }

    // Method to move current test results to archive directory
    private void moveCurrentResultsToArchive() {
        logger.info("Moving current results to archive");
        try {
            String currentResultsDir = System.getProperty("user.dir") + File.separator + "Current Test Results";
            String archiveResultsDir = System.getProperty("user.dir") + File.separator + "Archived Test Results";
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
            File archivedResultsDir = new File(archiveResultsDir);
            archivedResultsDir.mkdirs();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss");
            String timestamp = dateFormat.format(new Date());
            String currentResultsArchiveDir = archiveResultsDir + File.separator + dateFormat2.format(new Date());
            File currentResultsArchive = new File(currentResultsArchiveDir);
            currentResultsArchive.mkdirs();
            Files.move(Paths.get(currentResultsDir), Paths.get(currentResultsArchiveDir + File.separator + timestamp));
            Files.createDirectory(Paths.get(currentResultsDir));
        } catch (IOException e) {
            logger.error("Error moving current results to archive", e);
        }
    }
}
