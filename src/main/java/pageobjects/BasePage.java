package pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.actions;

public abstract class BasePage {

    @Step
    public void clickUsingJS(SelenideElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    @Step
    public void scrollToViewUsingJS(SelenideElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Step
    public Long getPositionOfElementUsingJS(SelenideElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        return (Long) executor.executeScript("return arguments[0].offsetTop;", element);
    }

    @Step
    public void getTheFullScreen() {
        WebDriverRunner.getWebDriver().manage().window().fullscreen();
    }

    @Step
    public void resizeWindow(SelenideElement selenideElement, int pixelsX, int pixelsY) {
        selenideElement.shouldBe(Condition.exist, Duration.ofMillis(30000));
        actions().clickAndHold(selenideElement)
                .pause(Duration.ofMillis(2000))
                .moveByOffset(pixelsX, pixelsY)
                .pause(Duration.ofMillis(2000))
                .release()
                .pause(Duration.ofMillis(2000))
                .build()
                .perform();
    }

    @Step
    public void dragAndDropWindow(SelenideElement source, int xOffset, int yOffset) {
        source.shouldBe(Condition.exist, Duration.ofMillis(30000));
        actions().dragAndDropBy(source, xOffset, yOffset)
                .pause(Duration.ofMillis(2000))
                .build()
                .perform();
    }
}
