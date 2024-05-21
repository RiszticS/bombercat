package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public class PowerUpBombRange extends PowerUp {
    private StaticGraphics sg;

    public PowerUpBombRange(MatrixPosition p) {
        super(p);
        startDraw();
    }

    @Override
    public void apply(Player p) {
        for (Bomb bomb : p.getBombs()) {
            bomb.increaseExplosionRange();
        }
        GraphicsController.removeManager(sg);
        setUsed(true); // Jelöljük, hogy a powerup felhasználásra került
        p.removePowerUp(PowerUpBombRange.this); // Töröljük a power-upot a játékos listájából
    }

    @Override
    public void startDraw() {
        int tileSize = GraphicProperties.getTileSize();
        sg = new StaticGraphics("/images/extendedExplosion.png", this.position.convertToCoordinatePosition(tileSize), tileSize, tileSize);
        GraphicsController.addManager(sg);
    }
}