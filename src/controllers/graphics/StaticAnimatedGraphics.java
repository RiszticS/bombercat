package controllers.graphics;

import models.Position;

import java.awt.*;

public class StaticAnimatedGraphics implements GraphicsManager {
    private int imageDelay;
    private final SpriteSheet tileSheet;
    private int frameSwitch;
    private int width;
    private int height;
    private Position position;
    public StaticAnimatedGraphics(String path, int xAmount, int yAmount, int count, int gap, int width, int height, int speed, Position position) {
        this.tileSheet = new SpriteSheet(path, xAmount, yAmount, count, gap, width, height, speed);
        this.imageDelay = 0;
        this.frameSwitch = speed;
        this.width = width;
        this.height = height;
        this.position = position;
        System.out.println("Frame time: " + frameSwitch);
    }

    @Override
    public void draw(Graphics2D g2) {
        int x = this.position.getX();
        int y = this.position.getY();
        if (imageDelay == frameSwitch) {
            g2.drawImage(tileSheet.next(), x, y, this.width * 2, this.height * 2, null);
            imageDelay = 0;
        } else {
            g2.drawImage(tileSheet.current(), x, y, this.width * 2, this.height * 2, null);
            imageDelay++;
        }
    }
}
