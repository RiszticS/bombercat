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

public class Chest extends FixedElement {
    private final StaticGraphics sg;

    public Chest(MatrixPosition p) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/chest.png",
                position.convertToCoordinatePosition(tileSize),
                tileSize);
        GraphicsController.addManager(sg);
    }

    public void explode(FixedElement[][] board) {
        GraphicsController.removeManager(sg);
        board[position.getX()][position.getY()] = new EmptyTile(position);
    }

    @Override
    public String getType() {
        return "Chest";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
