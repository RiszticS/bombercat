package model;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class Bomb extends Entity {
    private int x;
    private int y;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.position = new Position(x * 48, y * 48);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/bomb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawImage(image, x, y,48, 48, null);
    }
}