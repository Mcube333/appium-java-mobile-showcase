package com.mudassir.mobile.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutCompleteScreen extends BaseScreen {

    private static final By SCREEN = a11y("checkout complete screen");

    public CheckoutCompleteScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    @Step("Continue shopping")
    public ProductsScreen continueShopping() {
        scrollTo("Continue Shopping button");
        tap(a11y("Continue Shopping button"));
        return new ProductsScreen(driver);
    }
}
