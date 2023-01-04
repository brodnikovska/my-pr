package ui;

import beans.User;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.junit5.ReportPortalExtension;
import entities.LeftSideBar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pageobjects.DashboardsPage;
import pageobjects.LeftSidePanel;
import pageobjects.LoginPage;
import utils.PropertyController;

import static com.codeborne.selenide.Selenide.page;

@ExtendWith(ReportPortalExtension.class)
public class WidgetResizeTest extends BaseTestCase {

    private LoginPage loginPage = page(LoginPage.class);
    private LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
    private DashboardsPage dashboardsPage = page(DashboardsPage.class);
    private static final String TEST_DASHBOARD_NAME = "!wRRV";
    private static final String TEST_WIDGET_NAME = "DEMO_FILTER_752";
    private static final String OTHER_WIDGET_NAME = "DEMO_FILTER_2000";
    private static final String USERNAME = PropertyController.getPropertyByKey("user.name");
    private static final String PASSWORD = PropertyController.getPropertyByKey("user.password");
    private static final int X_OFFSET = 100;
    private static final int Y_OFFSET = 90;

    @BeforeEach()
    public void login(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        loginPage
                .login(user)
                .verifyLoginSuccessful();
    }

    @AfterEach()
    public void logout(){
        WebDriverRunner.closeWebDriver();
    }

    @Test()
    public void verifySelectedSizeIsSaved() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        int widgetWidthBeforeTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        int widgetHeightBeforeTransform = dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        int widgetWidthAfterTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        int widgetHeightAfterTransform = dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME);
        Assertions.assertTrue(widgetWidthAfterTransform > widgetWidthBeforeTransform);
        Assertions.assertTrue(widgetHeightAfterTransform > widgetHeightBeforeTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
        Assertions.assertTrue(dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME) < widgetWidthAfterTransform);
        Assertions.assertTrue(dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME) < widgetHeightAfterTransform);
    }

    @Test()
    public void verifyContentOfWidgetResizes() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        int numberOfTestRunsBeforeTransform = dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        int numberOfTestRunsAfterTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        Assertions.assertTrue(numberOfTestRunsBeforeTransform < numberOfTestRunsAfterTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
        Assertions.assertTrue(dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME) < numberOfTestRunsAfterTransform);
    }

    @Test()
    public void verifyScrollApplicableAfterResize() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
        Long positionOfLastRunBeforeScroll = dashboardsPage.getYPositionOfTestRun(TEST_WIDGET_NAME, dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME) -1);

        dashboardsPage.scrollToTestRun(TEST_WIDGET_NAME,  dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME) -1);
        Assertions.assertTrue(dashboardsPage.getYPositionOfTestRun(TEST_WIDGET_NAME, 1) < positionOfLastRunBeforeScroll);

        dashboardsPage.scrollToTestRun(TEST_WIDGET_NAME,  0);
        Assertions.assertEquals(dashboardsPage.getYPositionOfTestRun(TEST_WIDGET_NAME, dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME) -1), positionOfLastRunBeforeScroll);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
    }

    @Test()
    public void verifyOtherWidgetsMoveWhileResizing() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        float otherWidgetPositionXBeforeTransform = dashboardsPage.getWidgetPositionX(OTHER_WIDGET_NAME);
        float otherWidgetPositionYBeforeTransform = dashboardsPage.getWidgetPositionY(OTHER_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        float otherWidgetPositionXAfterTransform = dashboardsPage.getWidgetPositionX(OTHER_WIDGET_NAME);
        float otherWidgetPositionYAfterTransform = dashboardsPage.getWidgetPositionY(OTHER_WIDGET_NAME);
        Assertions.assertNotEquals(otherWidgetPositionXBeforeTransform, otherWidgetPositionXAfterTransform);
        Assertions.assertNotEquals(otherWidgetPositionYBeforeTransform, otherWidgetPositionYAfterTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
    }

    @Test()
    public void verifyWidgetMoveAfterDragAndDrop() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        float widgetPositionXBeforeTransform = dashboardsPage.getWidgetPositionX(TEST_WIDGET_NAME);
        float widgetPositionYBeforeTransform = dashboardsPage.getWidgetPositionY(TEST_WIDGET_NAME);

        dashboardsPage
                .dragAndDropWidget(TEST_WIDGET_NAME, X_OFFSET * 2, 0)
                .dragAndDropWidget(TEST_WIDGET_NAME, 0, Y_OFFSET * 3);
        Assertions.assertTrue(dashboardsPage.getWidgetPositionX(TEST_WIDGET_NAME) > widgetPositionXBeforeTransform);
        Assertions.assertTrue( dashboardsPage.getWidgetPositionY(TEST_WIDGET_NAME) > widgetPositionYBeforeTransform);

        dashboardsPage
                .dragAndDropWidget(TEST_WIDGET_NAME, -X_OFFSET * 2, 0)
                .dragAndDropWidget(TEST_WIDGET_NAME, 0, -Y_OFFSET * 2);
        Assertions.assertEquals(dashboardsPage.getWidgetPositionY(TEST_WIDGET_NAME), widgetPositionXBeforeTransform);
        Assertions.assertEquals( dashboardsPage.getWidgetPositionY(TEST_WIDGET_NAME), widgetPositionYBeforeTransform);
    }
}
