package main.controllers.graphics;

import main.controllers.configuration.GraphicProperties;
import main.model.positions.Direction;
import main.model.positions.Position;
import main.model.graphics.AnimationConfiguration;
import main.model.graphics.SpriteSheet;

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
    public MovingAnimationGraphics(ArrayList<AnimationConfiguration> sheets, Position position, double scale) {
        this.tileSheets = new ArrayList<>();
        this.position = position;
        this.scale = scale;

        for (AnimationConfiguration config : sheets) {
            tileSheets.add(new SpriteSheet(config.path(), config.xAmount(), config.yAmount(), config.count(), config.gap(), config.width(), config.height(), config.speed()));
        }

        this.currentSheet = tileSheets.getFirst();
        currentIndex = 0;
        this.imageDelay = 0;
        this.frameSwitch = currentSheet.getSpeed();
    }

    public void changeDirection(Direction direction) {
        if(currentIndex != direction.getIndex()) {
            currentSheet = tileSheets.get(direction.getIndex());
            currentSheet.reset();
            this.frameSwitch = currentSheet.getSpeed();
            currentIndex = direction.getIndex();
            imageDelay = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage toDraw;

        if (imageDelay == frameSwitch) {
            toDraw = this.currentSheet.next();
            imageDelay = 0;
        } else {
            imageDelay++;
            toDraw = this.currentSheet.current();
        }

        g2.drawImage(toDraw, this.position.getX(), this.position.getY(), (int)Math.round(this.currentSheet.getWidth() * scale * (GraphicProperties.getTileSize() / 64.0)),  (int)Math.round(this.currentSheet.getHeight() * scale * (GraphicProperties.getTileSize() / 64.0)), null);
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
