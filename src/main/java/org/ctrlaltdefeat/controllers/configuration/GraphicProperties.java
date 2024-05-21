package org.ctrlaltdefeat.controllers.configuration;

import java.util.Objects;

public class GraphicProperties {
    private static final ConfigurationManager cm = new ConfigurationManager("graphics.properties");

    public static int getTileSize() {
        return Integer.parseInt(cm.getProperty("tile_size"));
    }

    public static int getColNumber() {
        return Integer.parseInt(cm.getProperty("col_number"));
    }

    public static int getRowNumber() {
        return Integer.parseInt(cm.getProperty("row_number"));
    }
}
