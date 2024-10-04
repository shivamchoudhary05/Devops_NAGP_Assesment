package com.redbus.pages;

import java.util.ArrayList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;
import com.redbus.driversetup.DriverSetup;
import com.redbus.util.Utils;

public class RedBusHelpPage extends DriverSetup {

	Utils util = new Utils();
    
    // WebElements
    @FindBy(xpath = "//span[text()='Help']")
    WebElement Help;

    @FindBy(xpath = "(//span[@class='parentText'])[2]")
    WebElement TechnicalIssue;
    
    @FindBy(xpath = "//div[@class='chip'][1]")
    WebElement DeactivateAccount;
    
    @FindBy(xpath = "//*[@id='chatdiv']/div[6]/div[2]/div[1]/div/span")
    WebElement Gettext;

    @FindBy(xpath = "//*[@id='content']/div[1]/div/iframe")
    WebElement iframe;
    
    // Constructor
    public RedBusHelpPage() {
        PageFactory.initElements(driver, this);
    }

    // Method to verify help functionality
    public String verifyhelp() throws InterruptedException {
        Help.click();
        util.waitForElementImplicitly();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.switchTo().frame(iframe);
        TechnicalIssue.click();
        DeactivateAccount.click();
        util.waitForElementToBeVisible(Gettext);
        return Gettext.getText();
    }
}
