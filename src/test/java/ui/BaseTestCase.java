package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.junit5.ReportPortalExtension;
import utils.PropertyController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.SlackService;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseTestCase extends SlackService {

    @BeforeEach
    public void before() {
        Configuration.browser = PropertyController.getPropertyByKey("driver");
        open(PropertyController.getPropertyByKey("base.url"));
        postNotification("{\"text\":\"Hello, Test!\"}");
    }

    @AfterEach
    public void after() {
        postNotification("{\"text\":\"Goodbye, Test!\"}");
        WebDriverRunner.closeWebDriver();
    }
}
