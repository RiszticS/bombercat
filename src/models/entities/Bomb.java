package models.entities;

import models.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;



public class Bomb extends Entity {
    private ArrayList<Explosion> explosions;
    private int radius;
    private static final int EXPLOSION_DURATION = 2000;
    private static final int EXPLOSION_DELETE = 4000;
    private long startTime;
    private boolean deleted;

    public Bomb(int x, int y, int radius) {
        this.position = new Position(x, y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/bomb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), 64, 64);
        this.explosions = new ArrayList<>();
        this.radius = radius;
        this.deleted = false;
        startTimer();
    }

    private void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }
    private void startTimer() {
        startTime = System.currentTimeMillis();
    }
    public boolean hasExploded() {
        return System.currentTimeMillis() - startTime >= EXPLOSION_DURATION;
    }
    public boolean hasDeleted(){
        deleted =  System.currentTimeMillis() - startTime >= EXPLOSION_DELETE;
        return deleted;
    }

    private void handleExplosion() {
        this.image = null;
        int x[] = {0, 64, 0, -64, 0};
        int y[] = {0, 0, -64, 0, 64};
        for(int i = 0; i < 5; i++) {
            for (int j = 1; j <= radius; j++) {
                Explosion explosion = new Explosion(position.getX() + x[i] * j, position.getY() + y[i] * j, 2000);
                this.addExplosion(explosion);
            }
        }
    }

    public void removeExplosion(){
        this.image = null;
        for (int i = 0; i < explosions.size(); i++){
            explosions.set(i, null);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!hasExploded()){
            super.draw(g2);
        } else {
            if(explosions.isEmpty()){
                handleExplosion();
            }
            if (hasDeleted()){
                removeExplosion();
            }
            else {
                for (Explosion exp: explosions) {
                    exp.draw(g2);
                }
            }
        }
    }
}