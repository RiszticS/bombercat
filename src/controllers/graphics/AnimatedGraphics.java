package controllers.graphics;

import java.awt.*;

public class AnimatedGraphics implements GraphicsManager {
    private final SpriteSheet tileSheet;
    public AnimatedGraphics(String path, int xAmount, int yAmount, int gap, int count, int width, int height) {
        this.tileSheet = new SpriteSheet(path, xAmount, yAmount, gap, count, width, height);
    }

    public AnimatedGraphics(SpriteSheet tileSheet) {
        this.tileSheet = tileSheet;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(tileSheet.next(), tileSheet.getPosition().getX(), tileSheet.getPosition().getY(), tileSheet.getWidth(), tileSheet.getHeight(), null);
    }
}
