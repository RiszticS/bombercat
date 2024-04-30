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
    private StaticGraphics sg;
    public EmptyTile(MatrixPosition p, boolean addRender) {
        super(p);
        if (addRender) {
            int tileSize = GraphicProperties.getTileSize();
            sg = new StaticGraphics("/main/assets/images/floor.png", p.convertToCoordinatePosition(tileSize),tileSize);
            GraphicsController.addManagerFirst(sg);
        }
    }

    @Override
    public String getType() {
        return "EmptyTile";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
