package com.redbus.util;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.redbus.driversetup.DriverSetup;

public class Utils extends DriverSetup {
    
    // Global Weight from the config file for wait duration
    int weight = Integer.parseInt(envConfig.getProperty("globalweight"));

    /**
     * Wait for the element to be visible.
     * 
     * @param element WebElement to wait for visibility.
     */
    public void waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(weight));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for the element to be invisible.
     * 
     * @param element WebElement to wait for invisibility.
     */
    public void waitForElementToBeInvisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(weight));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait for the element to be clickable.
     * 
     * @param element WebElement to wait for clickability.
     */
    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(weight));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Set implicit wait for the driver.
     */
    public void waitForElementImplicitly() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(weight));
    }
    
    /**
     * Select an option from a dropdown by visible text.
     * 
     * @param element WebElement representing the dropdown.
     * @param text    Visible text of the option to select.
     */
    public void selectByVisibleText(WebElement element, String text) {
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(text);
    }

    /**
     * Select an option from a dropdown by value.
     * 
     * @param element WebElement representing the dropdown.
     * @param value   Value of the option to select.
     */
    public void selectByValue(WebElement element, String value) {
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
    }

    /**
     * Select the first option from a dropdown.
     * 
     * @param element WebElement representing the dropdown.
     */
    public void selectFirstValue(WebElement element) {
        Select dropdown = new Select(element);
        dropdown.selectByIndex(0);
    }

    /**
     * Get the selected value from a dropdown.
     * 
     * @param element WebElement representing the dropdown.
     * @return The selected value as a string.
     */
    public String getSelectedValue(WebElement element) {
        Select dropdown = new Select(element);
        return dropdown.getFirstSelectedOption().getText().trim();
    }

    /**
     * Get all the values from a dropdown.
     * 
     * @param element WebElement representing the dropdown.
     * @return Array of string containing all the values.
     */
    protected String[] getDropDownValues(WebElement element) {
        List<WebElement> allOptions = new Select(element).getOptions();
        String[] labels = new String[allOptions.size()];
        for (int i = 0; i < allOptions.size(); i++) {
            labels[i] = allOptions.get(i).getText();
        }
        return labels;
    }

    /**
     * Select an option from a dropdown by index.
     * 
     * @param element WebElement representing the dropdown.
     * @param index   Index of the option to select.
     */
    protected void selectDropDownByIndex(WebElement element, int index) {
        new Select(element).selectByIndex(index);
    }

    /**
     * Refresh the current page.
     */
    public void refresh() {
        driver.navigate().refresh();
        waitForLoad(driver);
    }

    /**
     * Navigate to the previous page.
     */
    public void goToPreviousPage() {
        driver.navigate().back();
        waitForLoad(driver);
    }

    /**
     * Click on an element using JavaScript Executor.
     * 
     * @param element WebElement to be clicked.
     */
    public void clickUsingJavaScriptExecutor(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Get the current URL of the page.
     * 
     * @return Current URL as a string.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Wait for the page to load completely.
     * 
     * @param driver WebDriver instance.
     */
    public void waitForLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Scroll to the bottom of the page using JavaScript.
     */
    public void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Scroll to the top of the page using JavaScript.
     */
    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }
}
