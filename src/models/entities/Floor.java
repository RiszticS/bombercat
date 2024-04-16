package models.entities;

import controllers.configuration.GraphicProperties;
import models.Position;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Floor extends Entity {
    public Floor(int x, int y) {
        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/floor.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
