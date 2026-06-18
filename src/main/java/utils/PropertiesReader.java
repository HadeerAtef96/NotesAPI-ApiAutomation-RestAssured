package utils;

import io.qameta.allure.Step;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public static String filePath = "src/main/resources/settings.properties";

    @Step
    public static Properties loadConfigurationsIntoSystemProperties() {
        Properties properties = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.getProperties().putAll(properties);
        return properties;
    }

    public static String getPropertiesValue(String key){
        return System.getProperty(key);
    }
}
