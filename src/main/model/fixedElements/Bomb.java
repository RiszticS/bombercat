package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.game.RenderTimer;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;


public class Bomb extends FixedElement {
    private BufferedImage image;
    private final RenderTimer explosionCountdown;
    private int explosionStrength;
    private StaticGraphics sg;

    public Bomb(MatrixPosition p) {
        super(p);

        explosionCountdown = new RenderTimer(240);
        this.explosionStrength = 2;
    }

    public void plant(MatrixPosition position) {
        this.position = position;
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/bomb.png", position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);
        hitbox.setPosition(this.position.convertToCoordinatePosition(GraphicProperties.getTileSize()));
        explosionCountdown.start();
    }

    private void explode(FixedElement[][] board) {
        GraphicsController.removeManager(sg);
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