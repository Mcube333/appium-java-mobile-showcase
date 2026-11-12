package com.mudassir.mobile.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginScreen extends BaseScreen {

    private static final By SCREEN = a11y("login screen");
    private static final By USERNAME = a11y("Username input field");
    private static final By PASSWORD = a11y("Password input field");
    private static final By LOGIN_BUTTON = a11y("Login button");

    public LoginScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    @Step("Log in as '{username}'")
    public LoginScreen enterCredentialsAndSubmit(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        hideKeyboard();
        tap(LOGIN_BUTTON);
        return this;
    }

    public ProductsScreen loginAs(String username, String password) {
        enterCredentialsAndSubmit(username, password);
        return new ProductsScreen(driver);
    }

    public CheckoutAddressScreen loginDuringCheckout(String username, String password) {
        enterCredentialsAndSubmit(username, password);
        return new CheckoutAddressScreen(driver);
    }

    public boolean isErrorShown(String message) {
        return isShown(textContains(message));
    }
}
