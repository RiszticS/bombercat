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
        GraphicsController.addManager(new StaticGraphics("/main/assets/images/wall.png",
                this.position.convertToCoordinatePosition(GraphicProperties.getTileSize()),
                GraphicProperties.getTileSize(), GraphicProperties.getTileSize()));
    }

    public void draw(Graphics2D g2) {
        /*int tileSize = GraphicProperties.getTileSize();
        CoordinatePosition p = position.convertToCoordinatePosition(tileSize);
        g2.drawImage(image, p.getX(), p.getY(),tileSize, tileSize, null);
        g2.setColor(Color.RED);
        g2.drawRect(p.getX(), p.getY(),tileSize, tileSize);
        hitbox.draw(g2);*/
    }

    @Override
    public String getType() {
        return "Wall";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
