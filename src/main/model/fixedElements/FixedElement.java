package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.model.Hitbox;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class FixedElement {
    protected MatrixPosition position;
    protected Hitbox hitbox;

    public FixedElement(MatrixPosition p) {
        this.position = p;
        this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(GraphicProperties.getTileSize()));
    }

    public MatrixPosition getPosition() {
        return position;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public abstract String getType();

    public abstract void update(FixedElement[][] board);
}
