package com.redbus.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.redbus.driversetup.DriverSetup;
import com.redbus.util.Utils;

public class RedBusSearchBusPage extends DriverSetup {

    static WebDriver driver;
    Utils util = new Utils();

    // WebElements
    @FindBy(xpath = "(//*[@id='src'])")
    WebElement source;

    @FindBy(xpath = "(//*[@id='dest'])")
    WebElement destination;

    @FindBy(xpath = "(//*[@class='dateText'])")
    WebElement datepicker;

    @FindBy(xpath = "//h1")
    WebElement HomepageText;

    @FindBy(xpath = "(//*[@id='search_button'])")
    WebElement search;

    // Constructor
    public RedBusSearchBusPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method to set source location
    public void setSource(String location) {
        util.waitForElementToBeClickable(source);
        source.sendKeys(location);
    }

    // Method to set destination location
    public void setDestination(String location) {
        util.waitForElementToBeClickable(destination);
        destination.sendKeys(location);
    }

    // Method to select a date from the datepicker
    public void DatePicker(String date) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"onwardCal\"]/div/div")).click();
        
        String[] dateValue = date.split("-");

        String desired_month = dateValue[1];
        String desired_date = dateValue[0];

        while (true) {
            String actual_month = driver.findElement(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[1]/div[2]")).getText();
            if (actual_month.contains(desired_month)) {
                break;
            } else {
                driver.findElement(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[1]/div[3]")).click(); // next month
            }
        }

        int column_size = 7; // as per the days (Mon - Sun)
        int flag = 0;
        int row_size = driver.findElements(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div")).size(); 
        for (int i = 2; i <= row_size; i++) { // row
            for (int j = 1; j <= column_size; j++) { // column
                String actual_date = driver.findElement(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div[" + i + "]/span/div[" + j + "]")).getText();
                if (actual_date.equals(desired_date)) {
                    try {
                        driver.findElement(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div[" + i + "]/span/div[" + j + "]")).click();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    flag = 1; // set flag = 1
                    Thread.sleep(3000);
                    break; // breaking out of inner loop
                } else {
                    continue;
                }
            }
            if (flag == 1) { // selection of the date is done
                break;
            }
        }
    }

    // Method to click the search button
    public void clickSearch() {
        search.click();
    }

    // Method to perform bus search with source, destination, and date
    public void searchBus(String source, String destination, String date) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        setSource(source);
        util.waitForElementToBeClickable(this.destination);
        setDestination(destination);
        Thread.sleep(1000);
        datepicker.click();
        Thread.sleep(2000);
        DatePicker(date);
        clickSearch();
    }

    // Method to check if the search is successful
    public String isSearchSuccess() {
        return HomepageText.getText();
    }
}
