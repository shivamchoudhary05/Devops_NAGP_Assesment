package com.redbus.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.redbus.driversetup.DriverSetup;
import com.redbus.pages.RedBusSearchBusPage;
import com.redbus.util.ExcelUtil;

public class RedBusSearchBusTest extends DriverSetup {
    RedBusSearchBusPage searchBus;

    // DataProvider reading data from the excel for valid search test cases
    //Run Two Valid Case
    @DataProvider(name = "TestData1")
    public Object[][] TestData1() throws Exception {
        excelpath = envConfig.getProperty("excelpath");
        sheetname1 = envConfig.getProperty("sheetname1");
        String[][] testData = ExcelUtil.getExcelDataIn2DArray(excelpath, sheetname1);
        return testData;
    }

    // Test method for valid search
    @Test(priority = 1, dataProvider = "TestData1")
    public void validSearch(String From, String To, String Date, String ExpectedText) throws InterruptedException {
        logger.info("Performing Valid Search");
        test = extent.createTest("validSearch");
        test.log(Status.INFO, "Navigate to Red Bus URL <br>" + envConfig.getProperty("baseUrl"));
        searchBus = new RedBusSearchBusPage(driver);
        searchBus.searchBus(From, To, Date);
        Thread.sleep(1000);
        Assert.assertEquals(searchBus.isSearchSuccess(), ExpectedText);
        test.log(Status.INFO, "Searched Successfully");
    }

 // DataProvider reading data from the excel for invalid search test cases
    //Run Two invalid Case
    @DataProvider(name = "TestData")
    public Object[][] TestData() throws Exception {
        excelpath = envConfig.getProperty("excelpath");
        sheetname1 = envConfig.getProperty("sheetname");
        String[][] testData = ExcelUtil.getExcelDataIn2DArray(excelpath, sheetname1);
        return testData;
    }

    // Test method for invalid search
    @Test(priority = 0, dataProvider = "TestData")
    public void invalidSearch(String From, String To, String Date, String ExpectedText) throws InterruptedException {
        logger.info("Performing Invalid Search");
        test = extent.createTest("invalidSearch");
        test.log(Status.INFO, "Navigate to Red Bus URL <br>" + envConfig.getProperty("baseUrl"));
        searchBus = new RedBusSearchBusPage(driver);
        searchBus.searchBus(From, To, Date);
        Thread.sleep(1000);
        Assert.assertEquals(searchBus.isSearchSuccess(), ExpectedText);
        test.log(Status.INFO, "Remains on the home page and no search happens");
    }
}
