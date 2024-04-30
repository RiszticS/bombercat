package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Chest extends FixedElement {
    private BufferedImage image;
    private PowerUp powerUp;
    private final StaticGraphics sg;

    public Chest(MatrixPosition p) {
        super(p);
        sg = new StaticGraphics("/main/assets/images/chest.png", p.convertToCoordinatePosition(GraphicProperties.getTileSize()), GraphicProperties.getTileSize());
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
