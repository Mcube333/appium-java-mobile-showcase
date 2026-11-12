package com.mudassir.mobile.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MenuScreen extends BaseScreen {

    private static final By LOG_IN = a11y("menu item log in");
    private static final By LOG_OUT = a11y("menu item log out");
    private static final By CATALOG = a11y("menu item catalog");

    public MenuScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Menu: Log In")
    public LoginScreen goToLogin() {
        tap(LOG_IN);
        return new LoginScreen(driver);
    }

    @Step("Menu: Catalog")
    public ProductsScreen goToCatalog() {
        tap(CATALOG);
        return new ProductsScreen(driver);
    }

    @Step("Menu: Log Out and confirm")
    public LoginScreen logOut() {
        tap(LOG_OUT);
        acceptAlert(); // "Are you sure?"
        acceptAlert(); // "You are successfully logged out."
        return new LoginScreen(driver);
    }

    public boolean isLogOutShown() {
        return isShown(LOG_OUT);
    }

    public boolean isLogInShown() {
        return isShown(LOG_IN);
    }
}
