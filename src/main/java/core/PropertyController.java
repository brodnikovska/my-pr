package core;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class PropertyController {
    public static final String COMMON = "environment/common.properties";
    public static final String LOCAL = "environment/local.properties";
    public static final String WEB = "environment/web.properties";

    @SneakyThrows
    public static String getPropertyByKey(final String key) {
        final Properties properties = new Properties();
        InputStream inputStream;
        inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(COMMON);
        properties.load(inputStream);
        if (properties.containsKey(key)) {

        } else {
            inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(LOCAL);
            properties.load(inputStream);
        }
        return properties.getProperty(key);
    }
}
