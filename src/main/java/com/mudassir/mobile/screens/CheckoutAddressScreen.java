package com.mudassir.mobile.screens;

import com.mudassir.mobile.data.ShippingAddress;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutAddressScreen extends BaseScreen {

    private static final By SCREEN = a11y("checkout address screen");

    public CheckoutAddressScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    @Step("Enter shipping address")
    public CheckoutPaymentScreen enterAddress(ShippingAddress address) {
        type(a11y("Full Name* input field"), address.fullName());
        type(a11y("Address Line 1* input field"), address.addressLine1());
        type(a11y("City* input field"), address.city());
        hideKeyboard();
        scrollTo("Zip Code* input field");
        type(a11y("Zip Code* input field"), address.zipCode());
        type(a11y("Country* input field"), address.country());
        hideKeyboard();
        scrollTo("To Payment button");
        tap(a11y("To Payment button"));
        return new CheckoutPaymentScreen(driver);
    }
}
