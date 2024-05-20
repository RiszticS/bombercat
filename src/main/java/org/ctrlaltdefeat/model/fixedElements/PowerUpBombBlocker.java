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
    private Timer effectTimer;
    private static final int EFFECT_DURATION = 10000;

    public PowerUpBombBlocker(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/minusPowerUp.jpeg", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }

    public void apply(Player p) {
        System.out.println("blocker");
        p.setCanPlaceBomb(false);
        GraphicsController.removeManager(sg);
        effectTimer = new Timer();
        effectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                p.setCanPlaceBomb(true);
                effectTimer.cancel();
            }
        }, EFFECT_DURATION);
    }
}