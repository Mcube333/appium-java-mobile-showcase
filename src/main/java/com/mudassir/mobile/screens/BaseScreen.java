package com.mudassir.mobile.screens;

import com.mudassir.mobile.config.ConfigManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Shared screen helpers. Explicit waits only: no implicit waits, no Thread.sleep.
 * Locators use accessibility ids (content-desc) set by the app's developers, the most stable option.
 */
public abstract class BaseScreen {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BaseScreen(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.explicitWaitSeconds()));
    }

    protected static By a11y(String accessibilityId) {
        return AppiumBy.accessibilityId(accessibilityId);
    }

    protected static By textContains(String text) {
        return AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
    }

    protected WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> allVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected void tap(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void type(By by, String value) {
        WebElement field = visible(by);
        field.clear();
        field.sendKeys(value);
    }

    protected boolean isShown(By by) {
        try {
            return visible(by).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** RN wraps text in a child TextView; read it from the labelled container. */
    protected String childText(By container) {
        return visible(container).findElement(By.className("android.widget.TextView")).getText().trim();
    }

    /** Scrolls the first scrollable view until an element with this accessibility id is on screen. */
    protected void scrollTo(String accessibilityId) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().description(\"" + accessibilityId + "\"))"));
    }

    protected void hideKeyboard() {
        if (driver.isKeyboardShown()) {
            driver.hideKeyboard();
        }
    }

    /** Android system alert positive button (OK / LOG OUT). */
    protected void acceptAlert() {
        tap(By.id("android:id/button1"));
    }
}
