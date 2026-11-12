package com.mudassir.mobile.tests.listeners;

import com.mudassir.mobile.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/** On failure: device screenshot + UI hierarchy XML attached to Allure. */
public class FailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        attach();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.wasRetried()) {
            attach();
        }
    }

    private void attach() {
        if (!DriverFactory.hasDriver()) {
            return;
        }
        try {
            byte[] png = DriverFactory.driver().getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Device screenshot", "image/png", new ByteArrayInputStream(png), "png");
            Allure.addAttachment("UI hierarchy", "text/xml", DriverFactory.driver().getPageSource(), "xml");
        } catch (Exception e) {
            Allure.addAttachment("Evidence error", e.toString());
        }
    }
}
