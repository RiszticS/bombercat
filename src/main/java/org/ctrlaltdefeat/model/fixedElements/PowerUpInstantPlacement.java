package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUpInstantPlacement extends PowerUp {
    private StaticGraphics sg;
    private Timer effectTimer;
    private static final int EFFECT_DURATION = 15000;

    public PowerUpInstantPlacement(MatrixPosition p) {
        super(p);
        startDraw();
    }

    @Override
    public void apply(Player p) {
        System.out.println("instant");
        if (!p.getBombs().isEmpty()) {
            p.setPlantBombKeyPressed(true);
        }

        GraphicsController.removeManager(sg);
        effectTimer = new Timer();
        effectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                p.setPlantBombKeyPressed(false);
                effectTimer.cancel();
                setUsed(true); // Jelöljük, hogy a powerup felhasználásra került
                p.removePowerUp(PowerUpInstantPlacement.this); // Töröljük a power-upot a játékos listájából
            }
        }, 3000, EFFECT_DURATION);
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}