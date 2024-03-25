package controllers.graphics;

import models.Direction;

import java.awt.*;
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

        this.currentSheet = tileSheets.get(0);
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
        }
    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        if (imageDelay == frameSwitch) {
            g2.drawImage(this.currentSheet.next(), x, y, this.currentSheet.getWidth() * 2, this.currentSheet.getHeight() * 2, null);
            imageDelay = 0;
        } else {
            g2.drawImage(this.currentSheet.current(), x, y, this.currentSheet.getWidth() * 2, this.currentSheet.getHeight() * 2, null);
            imageDelay++;
        }
    }
}
