import com.codeborne.selenide.WebDriverRunner;
import core.PropertyController;
import core.WebDriverWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTestCase {

    @Before
    public void before() {
        WebDriverWrapper.setWebDriver(PropertyController.getPropertyByKey("driver"));
        WebDriverRunner.getWebDriver().navigate().to(PropertyController.getPropertyByKey("base.url"));
    }

    @After
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
