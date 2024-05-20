package main.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.fixedElements.PowerUp;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

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
        sg = new StaticGraphics("/images/minusPowerUp.jpeg", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
