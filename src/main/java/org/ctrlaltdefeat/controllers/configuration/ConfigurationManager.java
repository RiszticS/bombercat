package org.ctrlaltdefeat.controllers.configuration;

import java.io.*;
import java.util.Properties;

public class ConfigurationManager {
    private final Properties prop;
    private final String config;

    public ConfigurationManager(String config) {
        this.config = config;
        try {
            prop = new Properties();
            prop.load(new BufferedReader(new FileReader(System.getenv("game_path") + "/configurations/" + config)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file: " + config, e);
        }
    }

    public void reload(){
        try {
            prop.load(new BufferedReader(new FileReader(System.getenv("game_path") + "/configurations/" + config)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file: " + config, e);
        }
    }

    public String getProperty(String property) {
        return prop.getProperty(property);
    }

    public void changeProperty(String property, String newValue) {
        prop.setProperty(property, newValue);
        try (FileWriter output = new FileWriter(System.getenv("game_path") + "/configurations/" + config)){
            prop.store(output, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to configuration file: " + config, e);
        }
    }
}
