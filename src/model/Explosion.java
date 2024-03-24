package model;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Explosion extends Entity {
    private final long duration;
    public Explosion(int x, int y, long duration) {
        this.position = new Position(x, y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/explosion.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.duration = duration;
    }
}
