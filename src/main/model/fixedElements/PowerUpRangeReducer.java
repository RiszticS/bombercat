package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.game.RenderTimer;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
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
        }}

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
