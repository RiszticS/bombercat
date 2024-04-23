package main.controllers.graphics;

import main.controllers.configuration.GraphicProperties;
import main.model.positions.Direction;
import main.model.positions.Position;
import main.model.graphics.AnimationConfiguration;
import main.model.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovingAnimationGraphics implements GraphicsManager {
    private int imageDelay;
    private final ArrayList<SpriteSheet> tileSheets;
    private int frameSwitch;
    private SpriteSheet currentSheet;
    private int currentIndex;
    private final Position position;
    public MovingAnimationGraphics(ArrayList<AnimationConfiguration> sheets, Position position) {
        this.tileSheets = new ArrayList<>();
        this.position = position;

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

        g2.drawImage(toDraw, this.position.getX(), this.position.getY(), (int)Math.round(this.currentSheet.getWidth() * 2 * (GraphicProperties.getTileSize() / 64.0)),  (int)Math.round(this.currentSheet.getHeight() * 2 * (GraphicProperties.getTileSize() / 64.0)), null);
    }
}
