package org.ctrlaltdefeat.model.graphics;

import org.ctrlaltdefeat.model.positions.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteSheet {
    private final ArrayList<BufferedImage> images;
    private int iterator;
    private final int imageCount;
    private final int width;
    private final int height;
    private final int speed;
    private Position position;

    public SpriteSheet(String path, int xAmount, int yAmount, int count, int gap, int width, int height, int speed) {
        this.iterator = 0;
        this.imageCount = count;
        this.width = width;
        this.height = height;
        this.speed = speed;

        images = new ArrayList<>();
        BufferedImage sheet;
        try {
            sheet = ImageIO.read(getClass().getResource(path));

            for (int i = 0; i < yAmount; i++) {
                for(int j = 0; j < xAmount; j++) {
                    images.add(sheet.getSubimage((j * width),i * height + i * gap, width, height));

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

    public BufferedImage next() {
        iterator++;

        if(iterator == imageCount) {
            iterator = 0;
        }

        return images.get(iterator);
    }

    public void reset() {
        this.iterator = 0;
    }

    public BufferedImage current() {
        return images.get(iterator);
    }
}
