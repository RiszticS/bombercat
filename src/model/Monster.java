package model;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Monster extends Entity implements Movable {
    private final int speed;

    public Monster(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        speed = 4;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/monster.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move(Direction d) {
        if (d == Direction.UP) {
            this.position.changeY(-speed);
        } else if (d == Direction.RIGHT) {
            this.position.changeX(speed);
        } else if (d == Direction.LEFT) {
            this.position.changeX(-speed);
        } else if (d == Direction.DOWN) {
            this.position.changeY(speed);
        }
    }
}
