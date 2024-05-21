package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUpBombBlocker extends PowerUp {
    private StaticGraphics sg;
    private static final int EFFECT_DURATION = 10000;

    public PowerUpBombBlocker(MatrixPosition p) {
        super(p);
        startDraw();
    }

    @Override
    public void apply(Player p) {
        System.out.println("blocker");
        p.setCanPlaceBomb(false);
        GraphicsController.removeManager(sg);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                p.setCanPlaceBomb(true);
                setUsed(true); // Jelöljük, hogy a powerup felhasználásra került
                p.removePowerUp(PowerUpBombBlocker.this); // Töröljük a power-upot a játékos listájából
            }
        }, EFFECT_DURATION);
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}