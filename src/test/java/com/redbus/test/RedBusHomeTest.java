package com.redbus.test;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.redbus.driversetup.DriverSetup;
import com.redbus.pages.RedBusHomePage;
import com.redbus.util.RetryAnalyzer;
import com.redbus.util.Utils;

public class RedBusHomeTest extends DriverSetup {
    private static final Logger logger = Logger.getLogger(RedBusHomeTest.class);
    RedBusHomePage homepage;

    @Test()
    public void verifyHomePageDeafault() {

        Utils util = new Utils();
        logger.info("Verifying Home Page Default");
        test = extent.createTest("HomePageDeafault");
        homepage = new RedBusHomePage();
        util.waitForElementImplicitly();
        Assert.assertEquals(homepage.homePageDeafault(), envConfig.getProperty("homepage"));
        test.log(Status.INFO, "Home Page title and default tab verified");
   
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void verifyTrainPage() {

        Utils util = new Utils();
        logger.info("Verifying Train Page");
        test = extent.createTest("Verify Train Page");
        homepage = new RedBusHomePage();
        util.waitForElementImplicitly();
        Assert.assertEquals(homepage.trainPage(), envConfig.getProperty("trainpage"));
        test.log(Status.INFO, "Train Booking Page title");
    }

    @Test()
    public void verifyNaviagteToHomePageBreadcrumb() {

        Utils util = new Utils();
        logger.info("Verifying Navigation to Home Page using Breadcrumb");
        test = extent.createTest("Verify Navigate Back to Train Page");
        homepage = new RedBusHomePage();
        util.waitForElementImplicitly();
        Assert.assertEquals(homepage.navigateBacktoHomepageBreadcrumb(),
        		envConfig.getProperty("homepage"));
        test.log(Status.INFO, "User Navigated Back to Home Page using Breadcrumb Navigation");
    }

    @Test()
    public void verifyNaviagteToHomePageRedBusLogo() {

        Utils util = new Utils();
        logger.info("Verifying Navigation to Home Page using RedBus Logo");
        test = extent.createTest("Verify Navigate Back to Train Page");
        homepage = new RedBusHomePage();
        util.waitForElementImplicitly();
        Assert.assertEquals(homepage.navigateBacktoHomepageRedBusLogo(),
        		envConfig.getProperty("homepage"));
        test.log(Status.INFO, "User Navigated Back to Home Page using RedBusLogo");
    }

    @Test()
    public void verifyHelpPage() {

        Utils util = new Utils();
        logger.info("Verifying Help Page");
        test = extent.createTest("Help Page");
        homepage = new RedBusHomePage();
        util.waitForElementImplicitly();
        Assert.assertEquals(homepage.helpPage(), envConfig.getProperty("helppage"));
        test.log(Status.INFO, "Help Page title verified");
    }

}
