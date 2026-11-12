package com.mudassir.mobile.screens;

import com.mudassir.mobile.data.Prices;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class ProductsScreen extends BaseScreen {

    public enum Sort {
        NAME_ASC("nameAsc"), NAME_DESC("nameDesc"), PRICE_ASC("priceAsc"), PRICE_DESC("priceDesc");

        final String id;

        Sort(String id) {
            this.id = id;
        }
    }

    private static final By SCREEN = a11y("products screen");
    private static final By ITEM_NAME = a11y("store item text");
    private static final By ITEM_PRICE = a11y("store item price");
    private static final By SORT_BUTTON = a11y("sort button");

    public ProductsScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isShown(SCREEN);
    }

    public HeaderBar header() {
        return new HeaderBar(driver);
    }

    public List<String> visibleNames() {
        return allVisible(ITEM_NAME).stream().map(WebElement::getText).map(String::trim).toList();
    }

    public List<BigDecimal> visiblePrices() {
        return allVisible(ITEM_PRICE).stream().map(WebElement::getText).map(Prices::parse).toList();
    }

    @Step("Sort products: {sort}")
    public ProductsScreen sortBy(Sort sort) {
        tap(SORT_BUTTON);
        tap(a11y(sort.id));
        return this;
    }

    @Step("Open product #{index}")
    public ProductDetailsScreen openProduct(int index) {
        allVisible(ITEM_NAME).get(index).click();
        return new ProductDetailsScreen(driver);
    }
}
