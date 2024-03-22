package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends Entity {
    private int r;
    private int maxRadius;
    private long startTime;
    private long duration;
    private boolean expired;
    public Explosion(int x, int y, long duration) {
        this.r = 2;
        this.maxRadius = 5;
        this.position = new Position(x, y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/explosion.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
        this.expired = false;
    }

    public void removeImage(){
        this.image = null;
    }
}
