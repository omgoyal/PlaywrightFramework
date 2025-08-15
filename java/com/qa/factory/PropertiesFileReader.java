package com.qa.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {

    private final Properties propertyFile;

    public PropertiesFileReader(String filePath) {
        propertyFile = new Properties();
        try (FileInputStream stream = new FileInputStream(filePath)) {
            propertyFile.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file: " + filePath, e);
        }
    }

    /**
     * Returns the property value for the given key.
     *
     * @param propertyName the name of the property
     * @return the property value as a String
     */
    public String getPropertyValue(String propertyName) {
        return propertyFile.getProperty(propertyName);
    }

    /**
     * Checks if the property file contains a given key.
     *
     * @param key the key to check
     * @return true if the key is found
     */
    public boolean containsKey(String key) {
        return propertyFile.containsKey(key);
    }
}
