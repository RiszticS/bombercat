package org.ctrlaltdefeat.controllers.graphics;

import org.ctrlaltdefeat.model.graphics.SpriteSheet;
import org.ctrlaltdefeat.model.positions.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class StaticAnimatedGraphics implements GraphicsManager {
    private int imageDelay;
    private final SpriteSheet tileSheet;
    private final int frameSwitch;
    private final int width;
    private final int height;
    private final Position position;
    public StaticAnimatedGraphics(String path, int xAmount, int yAmount, int count, int gap, int width, int height, int speed, Position position) {
        this.tileSheet = new SpriteSheet(path, xAmount, yAmount, count, gap, width, height, speed);
        this.imageDelay = 0;
        this.frameSwitch = speed;
        this.width = width;
        this.height = height;
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage toDraw;

        if (imageDelay == frameSwitch) {
            toDraw = tileSheet.next();
            imageDelay = 0;
        } else {
            toDraw = tileSheet.current();
            imageDelay++;
        }

        g2.drawImage(toDraw, this.position.getX(), this.position.getY(), this.width * 2, this.height * 2, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaticAnimatedGraphics that)) return false;
        return imageDelay == that.imageDelay && frameSwitch == that.frameSwitch && width == that.width && height == that.height && Objects.equals(tileSheet, that.tileSheet) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageDelay, tileSheet, frameSwitch, width, height, position);
    }
}
