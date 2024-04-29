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

public class PowerUpRangeReducer extends PowerUp {
    private StaticGraphics sg;

    public PowerUpRangeReducer(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
    }

    @Override
    public void apply(Player p) {
        for (Bomb bomb : p.getBombs()) {
            int originalStrength = bomb.getExplosionStrength();
            bomb.setExplosionStrength(1);
            p.getEffectTimer().start();
            if (p.getEffectTimer().finished()) {
                    bomb.setExplosionStrength(originalStrength);
            } else {
                p.getEffectTimer().decrease();
            }

        }
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
