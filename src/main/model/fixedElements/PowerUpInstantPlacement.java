package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUpInstantPlacement extends PowerUp {
    private StaticGraphics sg;
    private Timer effectTimer;
    private static final int EFFECT_DURATION = 15000;

    public PowerUpInstantPlacement(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }


    @Override
    public void apply(Player p) {
        System.out.println("instant");
        if(!p.getBombs().isEmpty()){
            p.setPlantBombKeyPressed(true);
            //p.plantBomb(p.getBoard());
        }

        GraphicsController.removeManager(sg);
        effectTimer = new Timer();
        effectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                effectTimer.cancel();
                p.setPlantBombKeyPressed(false);
            }
        },3000, EFFECT_DURATION);
    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/minusPowerUp.jpeg", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
