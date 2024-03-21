package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity implements Movable {
    private final int speed = 4;
    private int boardX;
    private int boardY;

    public Player(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        this.boardX = x;
        this.boardY = y;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/astronautfront.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(Position p, BufferedImage i) {
        this.position = p;
        this.image = i;
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
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

    public void changeBoardPosition(int x, int y) {
        this.boardX = x;
        this.boardY = y;
    }

    public void placeBomb() {

    }
}
