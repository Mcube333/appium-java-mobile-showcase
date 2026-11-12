package com.mudassir.mobile.tests;

import com.mudassir.mobile.data.DemoUsers;
import com.mudassir.mobile.screens.LoginScreen;
import com.mudassir.mobile.screens.MenuScreen;
import com.mudassir.mobile.screens.ProductsScreen;
import com.mudassir.mobile.tests.base.BaseMobileTest;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Login")
public class LoginTest extends BaseMobileTest {

    @Test(groups = {"smoke"}, description = "Standard user logs in, sees Log Out in menu, then logs out")
    public void standardUserCanLoginAndLogout() {
        LoginScreen login = catalog().header().openMenu().goToLogin();
        ProductsScreen products = login.loginAs(DemoUsers.STANDARD_USER, DemoUsers.PASSWORD);
        assertThat(products.isDisplayed()).as("catalog shown after login").isTrue();

        MenuScreen menu = products.header().openMenu();
        assertThat(menu.isLogOutShown()).as("Log Out option visible").isTrue();

        LoginScreen afterLogout = menu.logOut();
        assertThat(afterLogout.isDisplayed()).as("back on login screen").isTrue();
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                {"", DemoUsers.PASSWORD, "Username is required"},
                {DemoUsers.STANDARD_USER, "", "Password is required"},
                {"nobody@example.com", "wrong-password", "Provided credentials do not match any user in this service."},
                {DemoUsers.LOCKED_USER, DemoUsers.PASSWORD, "Sorry, this user has been locked out."}
        };
    }

    @Test(dataProvider = "invalidLogins", groups = {"regression"},
            description = "Invalid logins show the right validation message")
    public void invalidLoginShowsError(String username, String password, String expectedError) {
        LoginScreen login = catalog().header().openMenu().goToLogin()
                .enterCredentialsAndSubmit(username, password);

        assertThat(login.isErrorShown(expectedError)).as("error '%s'", expectedError).isTrue();
        assertThat(login.isDisplayed()).as("still on login screen").isTrue();
    }
}
