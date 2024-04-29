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

public class PowerUpBombRange extends PowerUp {

    private StaticGraphics sg;

    public PowerUpBombRange(MatrixPosition p) {
        super(p);
    }

    @Override
    public void apply(Player p) {
        for (Bomb bomb : p.getBombs()) {
            bomb.increaseExplosionRange();
        }
        GraphicsController.removeManager(sg);
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/main/assets/images/plusRange.png", this.position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);
    }
}
