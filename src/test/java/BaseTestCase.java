import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.junit5.ReportPortalExtension;
import core.PropertyController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseTestCase {

    @BeforeEach
    public void before() {
        Configuration.browser = PropertyController.getPropertyByKey("driver");
        open(PropertyController.getPropertyByKey("base.url"));
    }

    @AfterEach
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
