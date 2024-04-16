package controllers.graphics;

import controllers.configuration.GraphicProperties;
import models.Direction;
import models.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovingAnimationGraphics implements GraphicsManager {
    private int imageDelay;
    private final ArrayList<SpriteSheet> tileSheets;
    private int frameSwitch;
    private SpriteSheet currentSheet;
    private int currentIndex;
    private Position postion;
    public MovingAnimationGraphics(ArrayList<AnimationConfiguration> sheets, Position position) {
        this.tileSheets = new ArrayList<>();
        this.postion = position;

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
    public void draw(Graphics2D g2) {

        BufferedImage toDraw;

        if (imageDelay == frameSwitch) {
            toDraw = this.currentSheet.next();
            imageDelay = 0;
        } else {
            imageDelay++;
            toDraw = this.currentSheet.current();
        }

        System.out.println("X: " + this.postion.getX() + ", Y: " + this.postion.getY());

        g2.drawImage(toDraw, this.postion.getX(), this.postion.getY(), (int)Math.round(this.currentSheet.getWidth() * 2 * (GraphicProperties.getTileSize() / 64.0)),  (int)Math.round(this.currentSheet.getHeight() * 2 * (GraphicProperties.getTileSize() / 64.0)), null);
    }
}
