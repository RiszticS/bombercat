package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUpRangeReducer extends PowerUp {
    private StaticGraphics sg;
    private static final int EFFECT_DURATION = 10000;
    private Timer effectTimer;

    public PowerUpRangeReducer(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }


    @Override
    public void apply(Player p) {
        System.out.println("távolság");
        GraphicsController.removeManager(sg);
        for (Bomb bomb : p.getBombs()) {
            int originalStrength = bomb.getExplosionStrength();
            bomb.setExplosionStrength(1);

            Timer effectTimer = new Timer();
            effectTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    bomb.setExplosionStrength(originalStrength);
                    effectTimer.cancel();
                }
            }, EFFECT_DURATION);
        }
    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/minusPowerUp.jpeg", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
