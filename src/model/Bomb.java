package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;



public class Bomb extends Entity {
    private ArrayList<Explosion> explosions;
    private static final int EXPLOSION_DURATION = 3000;
    private static final int EXPLOSION_DELETE = 6000; // 6 seconds
    private long startTime;
    private boolean exploded;

    public Bomb(int x, int y) {
        this.explosions = new ArrayList<>();
        this.position = new Position(x, y);
        this.exploded = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/bomb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        startTimer();
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public boolean hasExploded() {
        return System.currentTimeMillis() - startTime >= EXPLOSION_DURATION;
    }

    public void handleExplosion() {
        this.image = null;
        int x[] = {0, 48, 0, -48, 0};
        int y[] = {0, 0, -48, 0, 48};
        for(int i = 0; i < 5; i++){
            Explosion explosion = new Explosion(position.getX() + x[i], position.getY() + y[i], 3000); // 3 seconds
            this.addExplosion(explosion);
        }
    }

    public boolean isExploded() {
        return exploded;
    }

    private boolean delayed(){
        exploded =  System.currentTimeMillis() - startTime >= EXPLOSION_DELETE;
        return exploded;
    }

    public void removeExplosion(){
        this.image = null;
        for (int i = 0; i < explosions.size(); i++){
            explosions.set(i, null);
        }

        /*
        if(delayed()){
            System.out.println("torol");
            for (Explosion exp: explosions) {
                exp.removeImage();
            }
        }*/

    }

    @Override
    public void draw(Graphics2D g) {
        if(!hasExploded()){
            super.draw(g);
        } else {
            if(explosions.isEmpty()){
                handleExplosion();
            }
            if (delayed()){
                removeExplosion();
            }
            else {
                for (Explosion exp: explosions) {
                    exp.draw(g);
                }
            }

        }


        /*
        if (hasExploded()){
            handleExplosion();
            for (Explosion explosion : explosions) {
                explosion.draw(g);
            }
        }*/
    }
}