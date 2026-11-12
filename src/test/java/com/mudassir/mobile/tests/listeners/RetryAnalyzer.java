package com.mudassir.mobile.tests.listeners;

import com.mudassir.mobile.config.ConfigManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempts = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempts < ConfigManager.retryCount()) {
            attempts++;
            return true;
        }
        return false;
    }
}
