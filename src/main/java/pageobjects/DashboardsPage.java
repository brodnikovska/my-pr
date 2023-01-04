package pageobjects;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardsPage extends BasePage {

    private static Logger logger = LoggerFactory.getLogger(DashboardsPage.class);
    private static final String WIDGET_DRAG_AND_DROP = "(//div[text()='%s']/../../../../..//following::span[@class='react-resizable-handle react-resizable-handle-se'])[1]";
    private static final String WIDGET_WINDOW = "//div[text()='%s']/../../../../../..";
    private static final String WIDGET_HEADER = "//div[text()='%s']/../../../..";
    private static final String GRID_ROW = "//div[text()='%s']/../../../../../..//div[contains(@class,'gridRow__grid-row-wrapper')]";
    private static final String DASHBOARD = "(//a[text()='%s'])[2]";

    @Step
    public DashboardsPage openDashboard(String dashboardName) {
        $x(String.format(DASHBOARD, dashboardName)).shouldBe(Condition.visible, Duration.ofMillis(10000));
        $x(String.format(DASHBOARD, dashboardName)).click();
        getTheFullScreen();
        return this;
    }

    @Step
    public DashboardsPage resizeWidget(String widgetName, int pixelsX, int pixelsY) {
        resizeWindow($x(String.format(WIDGET_DRAG_AND_DROP, widgetName)), pixelsX, pixelsY);
        return this;
    }

    @Step
    public DashboardsPage dragAndDropWidget(String widgetName, int pixelsX, int pixelsY) {
        dragAndDropWindow($x(String.format(WIDGET_HEADER, widgetName)), pixelsX, pixelsY);
        return this;
    }

    @Step
    public DashboardsPage scrollToTestRun(String widgetName, int index) {
        scrollToViewUsingJS($$x(String.format(GRID_ROW, widgetName)).get(index));
        return this;
    }

    @Step
    public int getNumberOfVisibleRuns(String widgetName) {
        $$x(String.format(GRID_ROW, widgetName)).first().shouldBe(Condition.visible, Duration.ofMillis(6000));
        return $$x(String.format(GRID_ROW, widgetName)).filter(Condition.visible).size();
    }

    @Step
    public Long getYPositionOfTestRun(String widgetName, int index) {
        return getPositionOfElementUsingJS($$x(String.format(GRID_ROW, widgetName)).get(index));
    }

    @Step
    public int getWidgetWidth(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        return Integer.parseInt($x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("width")
                .replace("px", ""));
    }

    @Step
    public int getWidgetHeight(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        return Integer.parseInt($x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("height")
                .replace("px", ""));
    }

    @Step
    public float getWidgetPositionX(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        String coordinates = $x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("transform")
                .replace("matrix(1, 0, 0, 1, ", "")
                .replace(")", "")
                .replace(" ", "");
        int comma = coordinates.indexOf(",");
        return Float.parseFloat(coordinates.substring(0, comma));
    }

    @Step
    public float getWidgetPositionY(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        String coordinates = $x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("transform")
                .replace("matrix(1, 0, 0, 1, ", "")
                .replace(")", "")
                .replace(" ", "");
        int comma = coordinates.indexOf(",");
        return Float.parseFloat(coordinates.substring(comma + 1));
    }
}
