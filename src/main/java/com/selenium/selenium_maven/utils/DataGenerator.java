package com.selenium.selenium_maven.utils;

public class DataGenerator {

    public static String generateEmail() {
        return "user" + System.currentTimeMillis() + "@test.com";
    }

    public static String generatePassword() {
        return "Pwd@" + System.currentTimeMillis();
    }
}
