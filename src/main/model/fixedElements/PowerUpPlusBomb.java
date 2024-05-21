package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;

public class PowerUpPlusBomb extends PowerUp {
    private StaticGraphics sg;

    public PowerUpPlusBomb(MatrixPosition p) {
        super(p);
        startDraw();
    }

    @Override
    public void apply(Player p) {
        p.getBombs().add(new Bomb(new MatrixPosition(0, 0)));
        GraphicsController.removeManager(sg);
        setUsed(true);
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/plusBomb.png", this.position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);
    }
}
