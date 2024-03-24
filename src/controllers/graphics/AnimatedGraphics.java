package controllers.graphics;

import java.awt.*;

public class AnimatedGraphics implements GraphicsManager {
    private int imageDelay;
    private final SpriteSheet tileSheet;
    private int frameSwitch;
    private int width;
    private int height;
    public AnimatedGraphics(String path, int xAmount, int yAmount, int count, int gap, int width, int height, int speed) {
        this.tileSheet = new SpriteSheet(path, xAmount, yAmount, count, gap, width, height);
        this.imageDelay = 0;
        this.frameSwitch = speed;
        this.width = width;
        this.height = height;
        System.out.println("Frame time: " + frameSwitch);
    }

    public AnimatedGraphics(SpriteSheet tileSheet) {
        this.tileSheet = tileSheet;
    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        if (imageDelay == frameSwitch) {
            g2.drawImage(tileSheet.next(), x, y, this.width * 2, this.height * 2, null);
            imageDelay = 0;
        } else {
            g2.drawImage(tileSheet.current(), x, y, this.width * 2, this.height * 2, null);
            imageDelay++;
        }
    }
}
