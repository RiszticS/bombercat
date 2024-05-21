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
        configurationManager = new ConfigurationManager("graphics.properties");
    }

    @Test
    @Order(1)
    public void getProperty() {
        String value = configurationManager.getProperty("tile_size");
        assertEquals("48", value);
    }

    @Test
    @Order(2)
    public void changeProperty() throws IOException {
        configurationManager.changeProperty("tile_size", "1");

        String newValue = configurationManager.getProperty("tile_size");
        assertEquals("1", newValue);
    }

    @AfterEach
    public void restore() {
        configurationManager.changeProperty("tile_size", "48");
    }

}
