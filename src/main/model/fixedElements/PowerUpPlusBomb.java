package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PowerUpPlusBomb extends PowerUp {
    private StaticGraphics sg;

    public PowerUpPlusBomb(MatrixPosition p) {
        super(p);
        sg = null;
    }

    @Override
    public void apply(Player p) {
        p.getBombs().add(new Bomb(new MatrixPosition(0,0)));
        GraphicsController.removeManager(sg);
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/plusBomb.png", this.position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);
    }
}
