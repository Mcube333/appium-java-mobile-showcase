package com.mudassir.mobile.tests;

import com.mudassir.mobile.data.DemoUsers;
import com.mudassir.mobile.data.PaymentCard;
import com.mudassir.mobile.data.ShippingAddress;
import com.mudassir.mobile.screens.CheckoutAddressScreen;
import com.mudassir.mobile.screens.CheckoutCompleteScreen;
import com.mudassir.mobile.screens.LoginScreen;
import com.mudassir.mobile.screens.ProductDetailsScreen;
import com.mudassir.mobile.screens.ProductsScreen;
import com.mudassir.mobile.screens.ReviewOrderScreen;
import com.mudassir.mobile.tests.base.BaseMobileTest;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Checkout")
public class CheckoutTest extends BaseMobileTest {

    @Test(groups = {"smoke"}, description = "Guest is sent to login at checkout, then completes the order")
    public void guestLogsInAtCheckoutAndPlacesOrder() {
        ShippingAddress address = ShippingAddress.demo();

        ProductDetailsScreen details = catalog().openProduct(0).addToCart();
        LoginScreen login = details.header().openCart().proceedToCheckoutAsGuest();
        assertThat(login.isDisplayed()).as("guest redirected to login").isTrue();

        CheckoutAddressScreen addressScreen = login.loginDuringCheckout(DemoUsers.STANDARD_USER, DemoUsers.PASSWORD);
        assertThat(addressScreen.isDisplayed()).isTrue();

        ReviewOrderScreen review = addressScreen.enterAddress(address).enterCard(PaymentCard.dummy());
        assertThat(review.isDisplayed()).isTrue();
        assertThat(review.showsText(address.fullName())).as("address on review screen").isTrue();
        assertThat(review.showsText(address.city())).isTrue();

        CheckoutCompleteScreen complete = review.placeOrder();
        assertThat(complete.isDisplayed()).as("checkout complete").isTrue();

        ProductsScreen products = complete.continueShopping();
        assertThat(products.header().cartCount()).as("cart cleared after order").isZero();
    }
}
