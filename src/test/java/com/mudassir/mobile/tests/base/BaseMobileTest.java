package com.mudassir.mobile.tests.base;

import com.mudassir.mobile.config.ConfigManager;
import com.mudassir.mobile.driver.DriverFactory;
import com.mudassir.mobile.screens.ProductsScreen;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/** Fresh app session per test. Tests use driver() so parallel cloud runs never share a session. */
public abstract class BaseMobileTest {

    @BeforeMethod(alwaysRun = true)
    public void startApp(Method method) {
        DriverFactory.start(getClass().getSimpleName() + "." + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void stopApp(ITestResult result) {
        try {
            if (ConfigManager.isCloud() && DriverFactory.hasDriver()) {
                // Marks the session pass/fail in the LambdaTest dashboard
                DriverFactory.driver().executeScript("lambda-status=" + (result.isSuccess() ? "passed" : "failed"));
            }
        } finally {
            DriverFactory.quit();
        }
    }

    protected AndroidDriver driver() {
        return DriverFactory.driver();
    }

    /** The app opens on the catalog. */
    protected ProductsScreen catalog() {
        return new ProductsScreen(driver());
    }
}
