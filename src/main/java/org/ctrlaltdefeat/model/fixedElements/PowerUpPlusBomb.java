package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public class PowerUpPlusBomb extends PowerUp {
    private StaticGraphics sg;

    public PowerUpPlusBomb(MatrixPosition p) {
        super(p);
        GraphicsController.addManager(sg);
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

