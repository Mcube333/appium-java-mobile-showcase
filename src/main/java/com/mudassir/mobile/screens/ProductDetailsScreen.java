package com.mudassir.mobile.screens;

import com.mudassir.mobile.data.Prices;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.math.BigDecimal;

public class ProductDetailsScreen extends BaseScreen {

    private static final By SCREEN = a11y("product screen");
    private static final By TITLE = a11y("container header");
    private static final By PRICE = a11y("product price");
    private static final By PLUS = a11y("counter plus button");
    private static final By AMOUNT = a11y("counter amount");
    private static final String ADD_TO_CART = "Add To Cart button";

    public ProductDetailsScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    public HeaderBar header() {
        return new HeaderBar(driver);
    }

    public String title() {
        return childText(TITLE);
    }

    public BigDecimal price() {
        return Prices.parse(visible(PRICE).getText());
    }

    public int quantity() {
        return Integer.parseInt(childText(AMOUNT));
    }

    @Step("Increase quantity by {times}")
    public ProductDetailsScreen increaseQuantity(int times) {
        scrollTo("counter plus button");
        for (int i = 0; i < times; i++) {
            tap(PLUS);
        }
        return this;
    }

    @Step("Add to cart")
    public ProductDetailsScreen addToCart() {
        scrollTo(ADD_TO_CART);
        tap(a11y(ADD_TO_CART));
        return this;
    }
}
