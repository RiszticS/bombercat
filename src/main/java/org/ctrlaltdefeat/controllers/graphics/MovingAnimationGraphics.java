package org.ctrlaltdefeat.controllers.graphics;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.model.graphics.AnimationConfiguration;
import org.ctrlaltdefeat.model.graphics.SpriteSheet;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.model.positions.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class MovingAnimationGraphics implements GraphicsManager {
    private int imageDelay;
    private final ArrayList<SpriteSheet> tileSheets;
    private int frameSwitch;
    private SpriteSheet currentSheet;
    private int currentIndex;
    private final Position position;
    private final double scale;
    private boolean runToEnd;
    private boolean animationCompleted;

    public MovingAnimationGraphics(ArrayList<AnimationConfiguration> sheets, Position position, double scale) {
        this.tileSheets = new ArrayList<>();
        this.position = position;
        this.scale = scale;

        for (AnimationConfiguration config : sheets) {
            tileSheets.add(new SpriteSheet(config.path(), config.xAmount(), config.yAmount(), config.count(), config.gap(), config.width(), config.height(), config.speed()));
        }

        this.currentSheet = tileSheets.get(0);
        currentIndex = 0;
        this.imageDelay = 0;
        this.frameSwitch = currentSheet.getSpeed();
        this.runToEnd = false;
        this.animationCompleted = false;
    }

    public void changeDirection(Direction direction) {
        if(currentIndex != direction.getIndex()) {
            currentSheet = tileSheets.get(direction.getIndex());
            currentSheet.reset();
            this.frameSwitch = currentSheet.getSpeed();
            currentIndex = direction.getIndex();
            imageDelay = 0;
            runToEnd = false;  // Reset the flag when direction changes
            animationCompleted = false;
        }
    }

    public void runToEnd() {
        runToEnd = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage toDraw;

        if (!animationCompleted) {
            if (imageDelay == frameSwitch) {
                toDraw = this.currentSheet.next();
                imageDelay = 0;
                if (runToEnd && this.currentSheet.isLastFrame()) {
                    animationCompleted = true;
                }
            } else if(imageDelay == -1) {
                toDraw = this.currentSheet.current();
            } else {
                imageDelay++;
                toDraw = this.currentSheet.current();
            }
        } else {
            toDraw = this.currentSheet.current();
        }

        g2.drawImage(toDraw, this.position.getX(), this.position.getY(),
                (int) Math.round(this.currentSheet.getWidth() * scale * (GraphicProperties.getTileSize() / 64.0)),
                (int) Math.round(this.currentSheet.getHeight() * scale * (GraphicProperties.getTileSize() / 64.0)),
                null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovingAnimationGraphics that)) return false;
        return imageDelay == that.imageDelay && frameSwitch == that.frameSwitch && currentIndex == that.currentIndex && Double.compare(scale, that.scale) == 0 && Objects.equals(tileSheets, that.tileSheets) && Objects.equals(currentSheet, that.currentSheet) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageDelay, tileSheets, frameSwitch, currentSheet, currentIndex, position, scale);
    }
}
