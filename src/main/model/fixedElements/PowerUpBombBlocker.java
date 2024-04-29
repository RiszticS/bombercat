package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;
import java.awt.*;
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
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }

    public void apply(Player p) {
        System.out.println("blocker");
        GraphicsController.removeManager(sg);
        p.setCanPlaceBomb(false);
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