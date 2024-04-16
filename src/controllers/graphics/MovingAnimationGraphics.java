package controllers.graphics;

import controllers.configuration.GraphicProperties;
import models.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovingAnimationGraphics implements GraphicsManager {
    private int imageDelay;
    private final ArrayList<SpriteSheet> tileSheets;
    private int frameSwitch;
    private SpriteSheet currentSheet;
    private int currentIndex;
    public MovingAnimationGraphics(ArrayList<AnimationConfiguration> sheets) {
        this.tileSheets = new ArrayList<>();

        for (AnimationConfiguration config : sheets) {
            tileSheets.add(new SpriteSheet(config.getPath(), config.getxAmount(), config.getyAmount(), config.getCount(), config.getGap(), config.getWidth(), config.getHeight(), config.getSpeed()));
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
    public void draw(Graphics2D g2, int x, int y) {

        BufferedImage toDraw;

        if (imageDelay == frameSwitch) {
            toDraw = this.currentSheet.next();
            imageDelay = 0;
        } else {
            imageDelay++;
            toDraw = this.currentSheet.current();
        }

        g2.drawImage(toDraw, x, y, (int)Math.round(this.currentSheet.getWidth() * 2 * (GraphicProperties.getTileSize() / 64.0)),  (int)Math.round(this.currentSheet.getHeight() * 2 * (GraphicProperties.getTileSize() / 64.0)), null);
    }
}
