package utils;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class PropertyController {
    public static final String COMMON = "environment/common.properties";
    public static final String REPORTPORTAL = "reportportal.properties";
    public static final String LOCAL = "environment/local.properties";
    public static final String WEB = "environment/web.properties";

    @SneakyThrows
    public static String getPropertyByKey(final String key) {
        final Properties properties1 = new Properties();
        final Properties properties2 = new Properties();
        InputStream inputStreamFromCommon, inputStreamFromReportportal;
        inputStreamFromCommon = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(COMMON);
        inputStreamFromReportportal = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(REPORTPORTAL);
        properties1.load(inputStreamFromCommon);
        properties2.load(inputStreamFromReportportal);
        if (properties1.containsKey(key)) {

        } else if (properties2.containsKey(key)) {
            return properties2.getProperty(key);
        } else {
            inputStreamFromCommon = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(LOCAL);
            properties1.load(inputStreamFromCommon);
        }
        return properties1.getProperty(key);
    }
}
