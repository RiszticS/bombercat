package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PowerUp extends Entity{
    private boolean pickedUp;
    public PowerUp(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        this.pickedUp = false;
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), 48, 48);
    }

    public boolean isPickedUp() {
        return pickedUp;
    }
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
}
