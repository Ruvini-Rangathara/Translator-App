package com.example.translatorapp.util;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {
    private static Config instance;

    @Getter
    private final String key;

    private Config() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/api_keys.properties");
        properties.load(fileInputStream);
        key = properties.getProperty("apikey");
    }

    public static Config getInstance() throws IOException {
        return instance == null ? instance = new Config() : instance;
    }
}
