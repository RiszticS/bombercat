package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity implements Movable {
    private final int speed = 4;

    public Player(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_down_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(Position p, BufferedImage i) {
        this.position = p;
        this.image = i;
    }

    public void move(Direction d) {
        if (d == Direction.UP) {
            this.position.changeY(-speed);
        }  if (d == Direction.RIGHT) {
            this.position.changeX(speed);
        }  if (d == Direction.LEFT) {
            this.position.changeX(-speed);
        }  if (d == Direction.DOWN) {
            this.position.changeY(speed);
        }
    }

    public void placeBomb() {

    }
}
