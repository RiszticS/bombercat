package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends FixedElement {
    protected BufferedImage image;
    protected boolean pickedUp;
    protected boolean used;

    public PowerUp(MatrixPosition p) {
        super(p);
        pickedUp = false;
        used = false;
    }

    public abstract void apply(Player p);

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public void update(FixedElement[][] board) {
        if (pickedUp) {
            board[this.position.getX()][this.position.getY()] = new EmptyTile(this.position, true);
        }
    }

    @Override
    public String getType() {
        return "PowerUp";
    }

    public abstract void startDraw();
}
