package org.ctrlaltdefeat.controllers.configuration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationManagerTest {
    private ConfigurationManager configurationManager;
    private Properties testProperties;

    @BeforeEach
    public void setUp() throws IOException {
        configurationManager = new ConfigurationManager("test.properties");
    }

    @Test
    @Order(1)
    public void getProperty() {
        String value = configurationManager.getProperty("test_key");
        assertEquals("68", value);
    }

    @Test
    @Order(2)
    public void changeProperty() throws IOException {
        configurationManager.changeProperty("test_key", "1");

        String newValue = configurationManager.getProperty("test_key");
        assertEquals("1", newValue);
    }

    @AfterEach
    public void restore() {
        configurationManager.changeProperty("test_key", "68");
    }

}
