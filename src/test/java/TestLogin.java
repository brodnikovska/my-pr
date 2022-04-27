import beans.User;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import core.PropertyController;
import core.StringHelper;
import org.junit.Test;


import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.page;

@RunWith(DataProviderRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLogin extends BaseTestCase {

    @DataProvider
    public static Object[][] validDefaultCredentials() {
        return new Object[][]{
                {PropertyController.getPropertyByKey("user.name"), PropertyController.getPropertyByKey("user.password")},
                {PropertyController.getPropertyByKey("admin.name"), PropertyController.getPropertyByKey("admin.password")}
        };
    }

    @DataProvider
    public static Object[][] invalidCredentials() {
        return new Object[][]{
                {null, ""},
                {StringHelper.generateRandomString(1, StringHelper.getValidCharacters()),
                        StringHelper.generateRandomString(3, StringHelper.getValidCharacters())}
        };
    }

    @DataProvider
    public static Object[][] badCredentials() {
        return new Object[][]{
                {StringHelper.generateRandomString(2, StringHelper.getValidCharacters()),
                        StringHelper.generateRandomString(4, StringHelper.getValidCharacters())}
        };
    }

    @Test
    @UseDataProvider("invalidCredentials")
    @Order(1)
    public void loginWithInvalidCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyNoLogin();
    }

    @Test
    @UseDataProvider("badCredentials")
    @Order(2)
    public void loginWithBadCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginUnsuccessful();
    }

    @Test
    @UseDataProvider("validDefaultCredentials")
    @Order(3)
    public void loginWithValidDefaultCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginSuccessful();
    }
}
