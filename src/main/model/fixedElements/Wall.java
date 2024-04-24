package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.Hitbox;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Wall extends FixedElement {
    public Wall(MatrixPosition p) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        GraphicsController.addManager(new StaticGraphics("/main/assets/images/wall.png",
                this.position.convertToCoordinatePosition(tileSize),
                tileSize));
    }

    @Override
    public String getType() {
        return "Wall";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
