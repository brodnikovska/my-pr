package utils;

import io.qameta.allure.Step;
import org.apache.commons.text.RandomStringGenerator;

public class StringHelper {

    private static final String VALID_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~!*'();:@&=+$,/?%#[]";

    public static String getValidCharacters() {
        return VALID_CHARACTERS;
    }

    @Step
    public static String generateRandomString(int length, String symbols) {
        return new RandomStringGenerator.Builder()
                .selectFrom(symbols.toCharArray()).build().generate(length);
    }
}
