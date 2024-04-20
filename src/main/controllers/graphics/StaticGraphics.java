package main.controllers.graphics;

import main.model.Position;
import main.model.graphics.Sprite;

import java.awt.*;

public class StaticGraphics implements GraphicsManager {
    private final Sprite image;
    private Position position;
    public StaticGraphics(String path, Position position, int width, int height) {
        image = new Sprite(path, position, width, height);
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image.getImage(), image.getPosition().getX(), image.getPosition().getY(), image.getWidth(), image.getHeight(), null);
    }
}
