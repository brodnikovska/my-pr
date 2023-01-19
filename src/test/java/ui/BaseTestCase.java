package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.junit5.ReportPortalExtension;
import utils.JiraService;
import utils.PropertyController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.SlackService;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseTestCase {
    SlackService slackService = new SlackService();

    @BeforeEach
    public void before() {
        JiraService jiraService = new JiraService();
        Configuration.browser = PropertyController.getPropertyByKey("driver");
        open(PropertyController.getPropertyByKey("base.url"));
        slackService.postNotification("{\"text\":\"Hello, Test!\"}");
        jiraService.modifyDashboard("21");
    }

    @AfterEach
    public void after() {
        JiraService jiraService = new JiraService();
        slackService.postNotification("{\"text\":\"Goodbye, Test!\"}");
        jiraService.modifyDashboard("2");
        WebDriverRunner.closeWebDriver();
    }
}
