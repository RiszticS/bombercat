package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PowerUp extends FixedElement {
    private BufferedImage image;

    public PowerUp(MatrixPosition p) {
        super(p);

        int tileSize = GraphicProperties.getTileSize();

        StaticGraphics sg = new StaticGraphics("/main/assets/images/powerup.png", position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);
    }

    @Override
    public String getType() {
        return "PowerUp";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
