package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.model.Hitbox;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

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

    public void setPosition(MatrixPosition position) {
        this.position = position;
        this.hitbox.setPosition(position.convertToCoordinatePosition(GraphicProperties.getTileSize()));
    }
}
