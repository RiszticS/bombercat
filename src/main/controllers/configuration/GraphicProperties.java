package main.controllers.configuration;

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

    public static void changeTileSize(int newSize) {
        cm.changeProperty("tile_size", "" + newSize);
    }

    public static int getFPS() {
        return Integer.parseInt(cm.getProperty("FPS"));
    }
}
