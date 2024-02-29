package model;

import controller.ControlSet;
import controller.PlayerController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private final Position position;
    private final int speed = 4;
    private BufferedImage image;

    public Player() {
        this.position = new Position(100, 100);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/images/boy_down_1.png"));
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

    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.getX(), position.getY(),48, 48, null);

    }

    public Position getPosition() {
        return position;
    }
}
