package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class EmptyTile extends FixedElement {
    public EmptyTile(MatrixPosition p) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        GraphicsController.addManagerFirst(new StaticGraphics("/main/assets/images/floor.png", p.convertToCoordinatePosition(tileSize),tileSize));
    }

    @Override
    public String getType() {
        return "EmptyTile";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
