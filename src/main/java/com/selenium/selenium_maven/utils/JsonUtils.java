package com.selenium.selenium_maven.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtils {

    public static JsonNode readJson(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = JsonUtils.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);
            return mapper.readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file");
        }
    }
}
