package com.redbus.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY_COUNT = 1; // Maximum number of retries

    /**
     * Determines whether to retry a test method that has failed.
     * 
     * @param result The result of the test method that failed.
     * @return true if the test method should be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) { // Check if test failed
            if (count < MAX_RETRY_COUNT) { // Check if max retry count is reached
                count++;
                return true; // Retry the test
            }
        }
        return false; // Don't retry
    }
}
