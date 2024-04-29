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

    public PowerUpSpeedReducer(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }

    @Override
    public void apply(Player p) {
        int originalSpeed = p.getSpeed();
        int reducedSpeed = originalSpeed / 2;
        p.setSpeed(reducedSpeed);
        p.getEffectTimer().start();

        if (p.getEffectTimer().finished()) {
            p.setSpeed(originalSpeed);
        } else {
            p.getEffectTimer().decrease();
        }
    }

    @Override
    public void update(FixedElement[][] board) {
        super.update(board);
    }

    @Override
    public void draw(Graphics2D g2) {

    }
    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}
