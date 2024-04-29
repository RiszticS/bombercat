package main.model.movingElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.configuration.ModelProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.GraphicsManager;
import main.controllers.graphics.MovingAnimationGraphics;
import main.controllers.movement.CollisionManager;
import main.model.Hitbox;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;

import java.awt.*;

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
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
