package com.mudassir.mobile.driver;

import com.mudassir.mobile.config.ConfigManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/** One AndroidDriver per test thread; the same code runs on a local emulator or LambdaTest. */
public final class DriverFactory {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static AndroidDriver start(String testName) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformVersion(ConfigManager.get("platform.version"))
                .setDeviceName(ConfigManager.get("device.name"))
                .setAppPackage(ConfigManager.get("app.package"))
                .setAutoGrantPermissions(true)
                .setNoReset(false) // clean app state for every test
                .setNewCommandTimeout(Duration.ofSeconds(ConfigManager.getInt("new.command.timeout.seconds")));

        if (ConfigManager.isCloud()) {
            options.setApp(ConfigManager.get("lt.app"));
            options.setCapability("lt:options", lambdaTestOptions(testName));
        } else {
            Path apk = Paths.get(ConfigManager.get("app.path")).toAbsolutePath();
            options.setApp(apk.toString());
        }

        AndroidDriver driver = new AndroidDriver(serverUrl(), options);
        DRIVER.set(driver);
        return driver;
    }

    private static Map<String, Object> lambdaTestOptions(String testName) {
        Map<String, Object> lt = new HashMap<>();
        lt.put("username", ConfigManager.secret("LT_USERNAME"));
        lt.put("accessKey", ConfigManager.secret("LT_ACCESS_KEY"));
        lt.put("platformName", "android");
        lt.put("deviceName", ConfigManager.get("device.name"));
        lt.put("platformVersion", ConfigManager.get("platform.version"));
        lt.put("app", ConfigManager.get("lt.app"));
        lt.put("isRealMobile", true);
        lt.put("build", ConfigManager.get("lt.build"));
        lt.put("name", testName);
        lt.put("video", true);
        lt.put("devicelog", true);
        lt.put("w3c", true);
        return lt;
    }

    private static URL serverUrl() {
        try {
            return URI.create(ConfigManager.get("appium.server.url")).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Bad appium.server.url", e);
        }
    }

    public static AndroidDriver driver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("No driver on this thread. Did the test extend BaseMobileTest?");
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quit() {
        try {
            if (DRIVER.get() != null) {
                DRIVER.get().quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}
