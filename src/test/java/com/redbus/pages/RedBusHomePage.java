package com.redbus.pages;

import java.util.ArrayList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;
import com.redbus.driversetup.DriverSetup;
import com.redbus.util.Utils;

public class RedBusHomePage extends DriverSetup {

    Utils util = new Utils();

    // WebElements
    @FindBy(xpath = "//img[@alt='redbus logo']")
    WebElement RedBusLogo;

    @FindBy(xpath = "//h1")
    WebElement HomePageText;
    
    @FindBy(xpath = "//span[text()='Train Tickets']")
    WebElement TrainTicketsIcon;

    @FindBy(xpath = "//span[text()='Bus Tickets']")
    WebElement BusTicketsIcon;

    @FindBy(xpath = "//span[text()='redRail: Train Ticket Booking']")
    WebElement TrainPageText;

    @FindBy(xpath = "//a[text()='Home']")
    WebElement HomepageLink;

    @FindBy(xpath = "//span[text()='Help']")
    WebElement Help;

    @FindBy(xpath = "//div[@class='red-title']")
    WebElement HelpText;

    // Constructor
    public RedBusHomePage() {
        PageFactory.initElements(driver, this);
    }

    // Method to get default homepage text and tab
    public String homePageDeafault() {
        test.log(Status.INFO, "Red Bus Home Page");
        HomePageText.getText();
        test.log(Status.INFO, "Red Bus Default Tab Selected" + " " + BusTicketsIcon.getText());
        return HomePageText.getText();
    }

    // Method to navigate to the Help page
    public String helpPage() {
        test.log(Status.INFO, "Navigate to Help page");
        Help.click();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        test.log(Status.INFO, "Help page Title" + ": " + HelpText.getText());
        return HelpText.getText();
    }
    
    // Method to navigate to the Train page
    public String trainPage() {
        test.log(Status.INFO, "Navigate to Train page");
        TrainTicketsIcon.click();
        test.log(Status.INFO, "Train page Title" + " " + TrainPageText.getText());
        return TrainPageText.getText();
    }

    // Method to navigate back to the homepage using the breadcrumb link
    public String navigateBacktoHomepageBreadcrumb() {
        test.log(Status.INFO, "Navigate to Train page");
        TrainTicketsIcon.click();
        test.log(Status.INFO, "Train page Title" + " " + TrainPageText.getText());
        HomepageLink.click();
        return HomePageText.getText();
    }
    
    // Method to navigate back to the homepage using the RedBus logo
    public String navigateBacktoHomepageRedBusLogo() {
        test.log(Status.INFO, "Navigate to Train page");
        TrainTicketsIcon.click();
        test.log(Status.INFO, "Train page Title" + ": " + TrainPageText.getText());
        RedBusLogo.click();
        return HomePageText.getText();
    }
}
