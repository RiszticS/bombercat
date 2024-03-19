package model;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Chest extends Entity {
    public Chest(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
