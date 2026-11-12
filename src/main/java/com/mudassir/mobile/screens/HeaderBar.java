package com.mudassir.mobile.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Top bar shared by all screens: side menu + cart badge. */
public class HeaderBar extends BaseScreen {

    private static final By MENU = a11y("open menu");
    private static final By CART_BADGE = a11y("cart badge");

    public HeaderBar(AndroidDriver driver) {
        super(driver);
    }

    @Step("Open side menu")
    public MenuScreen openMenu() {
        tap(MENU);
        return new MenuScreen(driver);
    }

    @Step("Open cart")
    public CartScreen openCart() {
        tap(CART_BADGE);
        return new CartScreen(driver);
    }

    /** Number shown on the cart badge; 0 when the badge has no count. */
    public int cartCount() {
        List<WebElement> counts = visible(CART_BADGE).findElements(By.className("android.widget.TextView"));
        if (counts.isEmpty() || counts.get(0).getText().isBlank()) {
            return 0;
        }
        return Integer.parseInt(counts.get(0).getText().trim());
    }
}
