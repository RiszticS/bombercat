package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected Position position;
    protected BufferedImage image;
    protected Hitbox hitbox;
    protected int boardX;
    protected int boardY;


    public Position getPosition() {
        return position;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void changeBoardPosition(int x, int y) {
        this.boardX = x;
        this.boardY = y;
    }

    public void draw(Graphics2D g) {
        g.drawImage(this.image, this.position.getX(), this.position.getY(),48, 48, null);
    }
}
