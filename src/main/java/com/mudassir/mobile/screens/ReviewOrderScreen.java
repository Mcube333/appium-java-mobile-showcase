package com.mudassir.mobile.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ReviewOrderScreen extends BaseScreen {

    private static final By SCREEN = a11y("checkout review order screen");

    public ReviewOrderScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    public boolean showsText(String text) {
        return isShown(textContains(text));
    }

    @Step("Place order")
    public CheckoutCompleteScreen placeOrder() {
        scrollTo("Place Order button");
        tap(a11y("Place Order button"));
        return new CheckoutCompleteScreen(driver);
    }
}
