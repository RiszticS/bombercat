package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;

public class Chest extends FixedElement {
    private PowerUp powerUp;
    private final StaticGraphics sg;

    public Chest(MatrixPosition p) {
        super(p);
        sg = new StaticGraphics("/images/chest.png", p.convertToCoordinatePosition(GraphicProperties.getTileSize()), GraphicProperties.getTileSize());
        GraphicsController.addManagerFirst(sg);
        this.powerUp = null;
    }

    public void explode(FixedElement[][] board) {
        if (this.powerUp != null) {
            board[this.position.getX()][this.position.getY()] = this.powerUp;
            this.powerUp.startDraw();
        } else {
            board[this.position.getX()][this.position.getY()] = new EmptyTile(this.position, false);
        }

        GraphicsController.removeManager(sg);
    }

    @Override
    public String getType() {
        return "Chest";
    }

    @Override
    public void update(FixedElement[][] board) {

    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
        this.powerUp.setPosition(this.position);
    }
}
