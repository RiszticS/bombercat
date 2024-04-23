package main.model.movingElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.configuration.ModelProperties;
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
    protected MovingElement(CoordinatePosition position) {
        this.position = position;
        int tileSize = GraphicProperties.getTileSize();
        this.hitbox = new Hitbox(position, tileSize / 2, tileSize / 2, tileSize / 4, tileSize / 2);
        this.collisionManager = new CollisionManager(this);
        this.alive = true;
    }

    protected MovingElement(MatrixPosition p) {
        this.position = p.convertToCoordinatePosition(48);
        int tileSize = GraphicProperties.getTileSize();
        this.hitbox = new Hitbox(this.position, tileSize / 2, tileSize / 2, tileSize / 4, tileSize / 2);
        this.collisionManager = new CollisionManager(this);
        this.alive = true;
    }

    protected abstract void move();
    public void die() {
        this.alive = false;
        System.out.println("You died!");
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void draw(Graphics2D g2);

    public CoordinatePosition getPosition() {
        return position;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
