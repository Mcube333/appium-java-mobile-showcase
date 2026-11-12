package com.mudassir.mobile.screens;

import com.mudassir.mobile.data.Prices;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.math.BigDecimal;

public class CartScreen extends BaseScreen {

    private static final By SCREEN = a11y("cart screen");
    private static final By ROW = a11y("product row");
    private static final By REMOVE = a11y("remove item");
    private static final By TOTAL_ITEMS = a11y("total number");
    private static final By TOTAL_PRICE = a11y("total price");
    private static final By CHECKOUT = a11y("Proceed To Checkout button");
    private static final By GO_SHOPPING = a11y("Go Shopping button");

    public CartScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    public int rowCount() {
        return allVisible(ROW).size();
    }

    public int totalItems() {
        return Integer.parseInt(visible(TOTAL_ITEMS).getText().replaceAll("[^0-9]", ""));
    }

    public BigDecimal totalPrice() {
        return Prices.parse(visible(TOTAL_PRICE).getText());
    }

    @Step("Remove first cart item")
    public CartScreen removeFirstItem() {
        tap(REMOVE);
        return this;
    }

    public boolean isEmptyStateShown() {
        return isShown(GO_SHOPPING);
    }

    @Step("Proceed to checkout as guest")
    public LoginScreen proceedToCheckoutAsGuest() {
        tap(CHECKOUT);
        return new LoginScreen(driver);
    }
}
