package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;

public class Chest extends FixedElement {
    private BufferedImage image;
    private PowerUp powerUp;
    private final StaticGraphics sg;

    public Chest(MatrixPosition p) {
        super(p);
        sg = new StaticGraphics("/images/tiles/spacestation/chest.png", p.convertToCoordinatePosition(GraphicProperties.getTileSize()), GraphicProperties.getTileSize());
        GraphicsController.addManager(sg);
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
