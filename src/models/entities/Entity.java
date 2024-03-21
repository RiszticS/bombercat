package models.entities;

import models.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected Position position;
    protected BufferedImage image;

    public Position getPosition() {
        return position;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, this.position.getX(), this.position.getY(),48, 48, null);
    }
}
