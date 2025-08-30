package com.mycompany.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("application-test.properties")) {
            
            if (input == null) {
                throw new RuntimeException("Unable to find application-test.properties");
            }
            
            properties.load(input);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test configuration", e);
        }
    }
    
    public static String getUsername() {
        return getProperty("test.username", "default_test_user");
    }
    
    public static String getPassword() {
        return getProperty("test.password", "default_test_pass");
    }
    
    public static String getProperty(String key, String defaultValue) {
        String envValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue;
        }
        
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue;
        }
        
        return properties.getProperty(key, defaultValue);
    }
    
    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}