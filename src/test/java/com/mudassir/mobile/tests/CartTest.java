package com.mudassir.mobile.tests;

import com.mudassir.mobile.screens.CartScreen;
import com.mudassir.mobile.screens.ProductDetailsScreen;
import com.mudassir.mobile.tests.base.BaseMobileTest;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Cart")
public class CartTest extends BaseMobileTest {

    @Test(groups = {"smoke"}, description = "Adding a product updates the cart badge")
    public void addToCartUpdatesBadge() {
        ProductDetailsScreen details = catalog().openProduct(0);
        assertThat(details.header().cartCount()).isZero();

        details.addToCart();
        assertThat(details.header().cartCount()).isEqualTo(1);
    }

    @Test(groups = {"regression"}, description = "Chosen quantity is carried into badge, cart count and total")
    public void quantityFlowsIntoCartTotals() {
        ProductDetailsScreen details = catalog().openProduct(0).increaseQuantity(2);
        assertThat(details.quantity()).isEqualTo(3);
        BigDecimal unitPrice = details.price();

        details.addToCart();
        assertThat(details.header().cartCount()).isEqualTo(3);

        CartScreen cart = details.header().openCart();
        assertThat(cart.rowCount()).isEqualTo(1);
        assertThat(cart.totalItems()).isEqualTo(3);
        assertThat(cart.totalPrice()).isEqualByComparingTo(unitPrice.multiply(BigDecimal.valueOf(3)));
    }

    @Test(groups = {"regression"}, description = "Removing the only item shows the empty cart state")
    public void removingLastItemEmptiesCart() {
        ProductDetailsScreen details = catalog().openProduct(0).addToCart();
        CartScreen cart = details.header().openCart();
        assertThat(cart.rowCount()).isEqualTo(1);

        cart.removeFirstItem();
        assertThat(cart.isEmptyStateShown()).as("'Go Shopping' empty state").isTrue();
    }
}
