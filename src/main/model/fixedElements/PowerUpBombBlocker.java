package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;

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
                setUsed(true);
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
