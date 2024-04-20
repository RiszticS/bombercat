package main.model.entities;

import main.controllers.configuration.GraphicProperties;
import main.model.Position;

public class PowerUp extends Entity {
    private boolean pickedUp;
    public PowerUp(int x, int y) {
        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        this.pickedUp = false;
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), GraphicProperties.getTileSize(), GraphicProperties.getTileSize());
    }

    public boolean isPickedUp() {
        return pickedUp;
    }
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
}
