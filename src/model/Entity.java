package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected Position position;
    protected BufferedImage image;
    protected Hitbox hitbox;
    public Position getPosition() {
        return position;
    }
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public void draw(Graphics2D g) {
        g.drawImage(this.image, this.position.getX(), this.position.getY(),48, 48, null);
    }
}
