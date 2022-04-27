import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.junit5.ReportPortalExtension;
import core.PropertyController;
import core.WebDriverWrapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseTestCase {

    @Before
    public void before() {
        WebDriverWrapper.setWebDriver(PropertyController.getPropertyByKey("driver"));
        WebDriverRunner.getWebDriver().navigate().to(PropertyController.getPropertyByKey("base.url"));
    }

    @AfterEach
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
