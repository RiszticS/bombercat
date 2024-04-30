package main.controllers.graphics;

import main.model.positions.Position;
import main.model.graphics.Sprite;

import java.awt.*;
import java.util.Objects;

public class StaticGraphics implements GraphicsManager {
    private final Sprite image;
    private Position position;
    public StaticGraphics(String path, Position position, int width, int height) {
        image = new Sprite(path, position, width, height);
        this.position = position;
    }

    public StaticGraphics(String path, Position position, int size) {
        image = new Sprite(path, position, size, size);
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image.getImage(), image.getPosition().getX(), image.getPosition().getY(), image.getWidth(), image.getHeight(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaticGraphics that)) return false;
        return Objects.equals(image, that.image) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, position);
    }
}
