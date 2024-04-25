package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.configuration.ModelProperties;
import main.controllers.game.RenderTimer;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class Bomb extends FixedElement {
    private BufferedImage image;
    private final RenderTimer explosionCountdown;
    private int explosionStrength;

    public Bomb(MatrixPosition p) {
        super(p);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/bomb.png"));
        } catch (IOException e) {
            this.image = null;
            System.out.println("Bomb image could not be found!");
        }
        explosionCountdown = new RenderTimer(240);
        this.explosionStrength = 2;
    }

    public void plant(MatrixPosition position) {
        this.position = position;
        hitbox.setPosition(this.position.convertToCoordinatePosition(GraphicProperties.getTileSize()));
        explosionCountdown.start();
    }

    private void explode(FixedElement[][] board) {
        Explosion explosion = new Explosion(position,this);
        explosion.spread(board);
    }

    @Override
    public void update(FixedElement[][] board) {
        if (explosionCountdown.finished()) {
            explode(board);
        } else {
            explosionCountdown.decrease();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int tileSize = GraphicProperties.getTileSize();
        CoordinatePosition p = position.convertToCoordinatePosition(tileSize);
        g2.drawImage(image, p.getX(), p.getY(),tileSize, tileSize, null);
        g2.setColor(Color.RED);
        g2.drawRect(p.getX(), p.getY(),tileSize, tileSize);
        hitbox.draw(g2);
    }

    @Override
    public String getType() {
        return "Bomb";
    }

    public int getExplosionStrength() {
        return explosionStrength;
    }

    public void setExplosionStrength(int explosionStrength) {
        this.explosionStrength = explosionStrength;
    }

    public boolean isAvailableToPlant() {
        return explosionCountdown.finished();
    }

    public MatrixPosition getPosition() {
        return position;
    }

    public RenderTimer getExplosionCountdown() {
        return explosionCountdown;
    }

    public void increaseExplosionRange() {
        this.explosionStrength++;
    }
}