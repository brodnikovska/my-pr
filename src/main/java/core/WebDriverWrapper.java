package core;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class WebDriverWrapper {

    public static void setWebDriver(String webdriver) {
        switch (webdriver) {
            case WebDriverRunner.CHROME:
                WebDriverManager.chromedriver().setup();
                System.setProperty("selenide.browser", WebDriverRunner.CHROME);
                break;
            case WebDriverRunner.EDGE:
                WebDriverManager.edgedriver().setup();
                System.setProperty("selenide.browser", WebDriverRunner.EDGE);
                break;
            case WebDriverRunner.FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                System.setProperty("selenide.browser", WebDriverRunner.FIREFOX);
                break;
            case WebDriverRunner.OPERA:
                WebDriverManager.operadriver().setup();
                System.setProperty("selenide.browser", WebDriverRunner.OPERA);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + webdriver);
        }
    };
}
