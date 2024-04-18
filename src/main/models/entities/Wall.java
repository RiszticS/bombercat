package main.models.entities;

import main.controllers.configuration.GraphicProperties;
import main.models.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Wall extends Entity {

    public Wall(int x, int y) {

        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), GraphicProperties.getTileSize(), GraphicProperties.getTileSize());
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/wall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
