package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.configuration.ModelProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.MovingAnimationGraphics;
import org.ctrlaltdefeat.controllers.movement.CollisionManager;
import org.ctrlaltdefeat.model.Hitbox;
import org.ctrlaltdefeat.model.positions.CoordinatePosition;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public abstract class MovingElement {
    protected final CoordinatePosition position;
    protected int speed = ModelProperties.getMovingElementDefaultSpeed();
    protected Hitbox hitbox;
    protected final CollisionManager collisionManager;
    protected boolean alive;
    protected MovingAnimationGraphics gm;
    protected MovingElement(CoordinatePosition position) {
        this.position = position;
        int tileSize = GraphicProperties.getTileSize();
        this.hitbox = new Hitbox(position, tileSize / 2, tileSize / 2, tileSize / 4, tileSize / 2);
        this.collisionManager = new CollisionManager(this);
        this.alive = true;
        this.gm = null;
    }

    protected MovingElement(MatrixPosition p) {
        this.position = p.convertToCoordinatePosition(48);
        int tileSize = GraphicProperties.getTileSize();
        this.hitbox = new Hitbox(this.position, tileSize / 2, tileSize / 2, tileSize / 4, tileSize / 2);
        this.collisionManager = new CollisionManager(this);
        this.alive = true;
        this.gm = null;
    }

    protected abstract void move();
    public void die() {
        System.out.println("Die");
        this.alive = false;
        GraphicsController.removeManager(gm);
    }

    public boolean isAlive() {
        return alive;
    }

    public CoordinatePosition getPosition() {
        return position;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    protected void setGraphicsManager(MovingAnimationGraphics gm) {
        this.gm = gm;
        GraphicsController.addManager(gm);
    }
}
