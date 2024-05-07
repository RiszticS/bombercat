package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;

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