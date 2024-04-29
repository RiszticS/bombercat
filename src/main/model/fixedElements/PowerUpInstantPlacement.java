package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class PowerUpInstantPlacement extends PowerUp {
    private StaticGraphics sg;
    private Timer effectTimer;

    public PowerUpInstantPlacement(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }
    @Override
    public void apply(Player p) {
        System.out.println("instant");
        GraphicsController.removeManager(sg);
        CoordinatePosition pos = p.getPosition();

        effectTimer = new Timer();
        effectTimer.scheduleAtFixedRate(new TimerTask() {
            private int elapsedTime = 0;
            @Override
            public void run() {
                MatrixPosition position = pos.convertToMatrixPosition(48);
                FixedElement[][] board = new FixedElement[position.getX()][position.getY()];
                elapsedTime++;
                p.setPlantBombKeyPressed(true);
                p.plantBomb(board);
                if (elapsedTime >= 10) {
                    effectTimer.cancel();
                    p.setPlantBombKeyPressed(false);
                }
            }
        }, 0, 1000);
    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
