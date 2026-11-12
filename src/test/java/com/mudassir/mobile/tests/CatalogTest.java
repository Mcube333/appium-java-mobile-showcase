package com.mudassir.mobile.tests;

import com.mudassir.mobile.screens.ProductDetailsScreen;
import com.mudassir.mobile.screens.ProductsScreen;
import com.mudassir.mobile.screens.ProductsScreen.Sort;
import com.mudassir.mobile.tests.base.BaseMobileTest;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Catalog")
public class CatalogTest extends BaseMobileTest {

    @Test(groups = {"regression"}, description = "Sort by name A-Z and Z-A orders visible products")
    public void sortByName() {
        ProductsScreen products = catalog().sortBy(Sort.NAME_ASC);
        List<String> ascending = products.visibleNames();
        assertThat(ascending).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        List<String> descending = products.sortBy(Sort.NAME_DESC).visibleNames();
        assertThat(descending).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed());
    }

    @Test(groups = {"regression"}, description = "Sort by price low-high and high-low orders visible products")
    public void sortByPrice() {
        ProductsScreen products = catalog().sortBy(Sort.PRICE_ASC);
        assertThat(products.visiblePrices()).isSortedAccordingTo(Comparator.naturalOrder());

        List<BigDecimal> descending = products.sortBy(Sort.PRICE_DESC).visiblePrices();
        assertThat(descending).isSortedAccordingTo(Comparator.<BigDecimal>naturalOrder().reversed());
    }

    @Test(groups = {"smoke"}, description = "Product details match the catalog card")
    public void productDetailsMatchCatalog() {
        ProductsScreen products = catalog();
        String name = products.visibleNames().get(0);
        BigDecimal price = products.visiblePrices().get(0);

        ProductDetailsScreen details = products.openProduct(0);
        assertThat(details.isDisplayed()).isTrue();
        assertThat(details.title()).isEqualTo(name);
        assertThat(details.price()).isEqualByComparingTo(price);
    }
}
