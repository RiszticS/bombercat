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

public class PowerUpSpeedReducer extends PowerUp {
    private StaticGraphics sg;
    private static final int EFFECT_DURATION = 10000;

    public PowerUpSpeedReducer(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }
    @Override
    public void apply(Player p) {
        System.out.println("sebesseg");
        int originalSpeed = p.getSpeed();
        int reducedSpeed = originalSpeed / 2;
        p.setSpeed(reducedSpeed);
        GraphicsController.removeManager(sg);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        p.setSpeed(originalSpeed);
                    }
                },
                EFFECT_DURATION
        );
    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
