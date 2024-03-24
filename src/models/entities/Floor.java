package models.entities;

import models.Position;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Floor extends Entity {
    public Floor(int x, int y) {
        this.position = new Position(x * 64, y * 64);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/floor.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
