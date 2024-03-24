package models.entities;

import models.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Wall extends Entity {

    public Wall(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), 48, 48);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/wall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
