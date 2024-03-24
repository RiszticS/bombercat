package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends Entity {
    private long startTime;
    private boolean expired;
    private long duration;

    public Explosion(int x, int y, long duration) {
        this.position = new Position(x, y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/explosion.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
        this.expired = false;
        startTimer();
    }
    private void startTimer() {
        startTime = System.currentTimeMillis();
    }
    public boolean isExpired(){
        expired =  System.currentTimeMillis() - startTime >= duration;
        return expired;
    }

}
