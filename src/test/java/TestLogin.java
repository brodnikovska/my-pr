import beans.User;
import com.epam.reportportal.junit5.ReportPortalExtension;
import core.PropertyController;
import core.StringHelper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageobjects.LoginPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(ReportPortalExtension.class)
public class TestLogin extends BaseTestCase {


    private static Stream<Arguments> validDefaultCredentials() {
        return Stream.of(
                arguments(PropertyController.getPropertyByKey("user.name"), PropertyController.getPropertyByKey("user.password")),
                arguments(PropertyController.getPropertyByKey("admin.name"), PropertyController.getPropertyByKey("admin.password")));
    }


    private static Stream<Arguments> invalidCredentials() {
        return Stream.of(
                arguments(null, ""),
                arguments(StringHelper.generateRandomString(1, StringHelper.getValidCharacters()),
                        StringHelper.generateRandomString(3, StringHelper.getValidCharacters())));
    }


    private static Stream<Arguments> badCredentials() {
        return Stream.of(
                arguments(
                        StringHelper.generateRandomString(2, StringHelper.getValidCharacters()),
                        StringHelper.generateRandomString(4, StringHelper.getValidCharacters())));
    }

    @ParameterizedTest
    @MethodSource("invalidCredentials")
    @Order(1)
    public void loginWithInvalidCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyNoLogin();
    }

    @ParameterizedTest
    @MethodSource("badCredentials")
    @Order(2)
    public void loginWithBadCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginUnsuccessful();
    }

    @ParameterizedTest
    @MethodSource("validDefaultCredentials")
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
