package main.controllers.configuration;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private Properties prop;
    private final String config;

    public ConfigurationManager(String config) {
        this.config = config;
        try (FileInputStream input = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/assets/configurations/" + this.config)){
            prop = new Properties();

            prop.load(input);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getProperty(String property) {
        return  prop.getProperty(property);
    }

    public void changeProperty(String property, String newValue) {
        prop.setProperty(property, newValue);
        try (FileWriter output = new FileWriter(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/assets/configurations/" + config)){
            prop.store(output, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
