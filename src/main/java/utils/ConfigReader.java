package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    // Load properties only once and return it
    public static Properties initProperties() {
        if (prop == null) {
            prop = new Properties();
            try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
                prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load config.properties");
            }
        }
        return prop; // return for backward compatibility
    }

    // Get a single property by key
    public static String getProperty(String key) {
        if (prop == null) {
            initProperties();
        }
        return prop.getProperty(key);
    }
}
