package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.movingElements.Player;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class PowerUp extends FixedElement {
    protected BufferedImage image;
    protected boolean pickedUp;

    public PowerUp(MatrixPosition p) {
        super(p);
        pickedUp = false;
    }

    public abstract void apply(Player p);

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public void update(FixedElement[][] board) {
        if (pickedUp) {
            board[this.position.getX()][this.position.getY()] = new EmptyTile(this.position);
        }
    }

    @Override
    public String getType() {
        return "PowerUp";
    }

    public abstract void startDraw();
}
