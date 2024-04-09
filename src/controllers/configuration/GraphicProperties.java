package controllers.configuration;

public class GraphicProperties {
    ConfigurationManager cm;

    public GraphicProperties() {
        cm = new ConfigurationManager("graphics.properties");
    }

    public int getTileSize() {
        return Integer.parseInt(cm.getProperty("tile_size"));
    }

    public int getColNumber() {
        return Integer.parseInt(cm.getProperty("col_number"));
    }

    public int getRowNumber() {
        return Integer.parseInt(cm.getProperty("row_number"));
    }

    public void changeTileSize(int newSize) {
        cm.changeProperty("tile_size", "" + newSize);
    }
}
