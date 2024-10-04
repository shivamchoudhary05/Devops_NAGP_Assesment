package com.redbus.test;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.redbus.driversetup.DriverSetup;
import com.redbus.pages.RedBusHelpPage;
import com.redbus.util.Utils;

public class RedBusHelpTest extends DriverSetup {

	RedBusHelpPage verifyHelp;
	
	 // Test to verify help functionality
	@Test()
	
	public void verifyTechnicalHelp() throws InterruptedException {
        Utils util = new Utils();
        logger.info("Verifying Technical Issue- Deactivate Account");
		test = extent.createTest("Verifying Technical Help");
		util.waitForElementImplicitly();
		verifyHelp = new RedBusHelpPage();
		Assert.assertEquals(verifyHelp.verifyhelp(), envConfig.getProperty("technicalhelp"));
        test.log(Status.INFO, "Verified Technical Issue- Deactivate Account when user not logged in");
	}

}