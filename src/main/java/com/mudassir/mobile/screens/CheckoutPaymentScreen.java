package com.mudassir.mobile.screens;

import com.mudassir.mobile.data.PaymentCard;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPaymentScreen extends BaseScreen {

    private static final By SCREEN = a11y("checkout payment screen");

    public CheckoutPaymentScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    @Step("Enter payment card")
    public ReviewOrderScreen enterCard(PaymentCard card) {
        type(a11y("Full Name* input field"), card.fullName());
        type(a11y("Card Number* input field"), card.number());
        type(a11y("Expiration Date* input field"), card.expiry());
        type(a11y("Security Code* input field"), card.securityCode());
        hideKeyboard();
        scrollTo("Review Order button");
        tap(a11y("Review Order button"));
        return new ReviewOrderScreen(driver);
    }
}
