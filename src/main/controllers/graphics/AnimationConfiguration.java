package main.controllers.graphics;

public class AnimationConfiguration {
    private final String path;
    private final int xAmount;
    private final int yAmount;
    private final int count;
    private final int gap;
    private final int width;
    private final int height;
    private final int speed;

    public AnimationConfiguration(String path, int xAmount, int yAmount, int count, int gap, int width, int height, int speed) {
        this.path = path;
        this.xAmount = xAmount;
        this.yAmount = yAmount;
        this.count = count;
        this.gap = gap;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public String getPath() {
        return path;
    }

    public int getxAmount() {
        return xAmount;
    }

    public int getyAmount() {
        return yAmount;
    }

    public int getCount() {
        return count;
    }

    public int getGap() {
        return gap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }
}
