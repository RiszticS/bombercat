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

public class PowerUpBombBlocker extends PowerUp {
    private StaticGraphics sg;
    public PowerUpBombBlocker(MatrixPosition p) {
        super(p);

    }

    @Override
    public void startDraw(){
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/minusPowerUp.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void apply(Player p) {
        p.setCanPlaceBomb(false);
        p.getEffectTimer().start();
        if (p.getEffectTimer().finished()) {
            p.setCanPlaceBomb(true);
        } else {
            p.getEffectTimer().decrease();
        }
    }
}