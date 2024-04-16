package models.entities;

import controllers.configuration.GraphicProperties;
import models.Position;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Chest extends Entity {
    public Chest(int x, int y) {
        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), GraphicProperties.getTileSize(), GraphicProperties.getTileSize());
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
