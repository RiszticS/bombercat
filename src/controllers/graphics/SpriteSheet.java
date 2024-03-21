package controllers.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteSheet {
    private ArrayList<BufferedImage> images;
    private int iterator;
    private int imageCount;
    private int gap;

    public SpriteSheet(String path, int x, int y, int count, int gap, int width, int height) {
        iterator = 0;
        imageCount = count;
        this.gap = gap;

        images = new ArrayList<>();
        BufferedImage sheet;
        try {
            sheet = ImageIO.read(getClass().getResource(path));

            for (int i = 0; i < y; i++) {
                for(int j = 0; j < x; j++) {
                    images.add(sheet.getSubimage((j * width) + (j * gap),i * height + i * gap, width, height));

                    if(images.size() == count) {
                        break;
                    }
                }
            }
            images.add(sheet);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage next() {
        iterator++;

        if(iterator == imageCount) {
            iterator = 0;
        }

        return images.get(iterator);
    }

    public void draw(Graphics2D g2) {
        for(int i = 0; i < imageCount; i++) {
            g2.drawImage(images.get(i), 48 * i, 48 * i,48, 48, null);
        }
    }
}
