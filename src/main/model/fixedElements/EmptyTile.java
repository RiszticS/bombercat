package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class EmptyTile extends FixedElement {
    public EmptyTile(MatrixPosition p) {
        super(p);
    }

    @Override
    public void draw(Graphics2D g2) {
        int tileSize = GraphicProperties.getTileSize();
        CoordinatePosition p = position.convertToCoordinatePosition(tileSize);
        g2.setColor(Color.BLACK);
        g2.fillRect(p.getX(), p.getY(), tileSize, tileSize);
        g2.setColor(Color.RED);
        g2.drawRect(p.getX(), p.getY(),tileSize, tileSize);
        this.hitbox.draw(g2);
    }

    @Override
    public String getType() {
        return "EmptyTile";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
