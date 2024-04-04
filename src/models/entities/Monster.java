package models.entities;

import models.Direction;
import models.Movable;
import models.Position;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Monster extends Entity implements Movable {
    private final int speed;
    private final Hitbox hitbox;
    private HashMap<Direction, Boolean> availableDirections;
    private Direction currentDirection;
    private Random random;

    public Monster(int x, int y) {
        random = new Random();
        this.position = new Position(x * 64, y * 64);
        this.boardX = x;
        this.boardY = y;
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), 64, 64);
        speed = random.nextInt(4) + 2;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/monster.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.availableDirections = new HashMap<>();
        availableDirections.put(Direction.UP, true);
        availableDirections.put(Direction.DOWN, true);
        availableDirections.put(Direction.LEFT, true);
        availableDirections.put(Direction.RIGHT, true);

        this.currentDirection = randomDirection();

    }

    private Direction randomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    public void move(Direction d) {
        if (d == Direction.UP && availableDirections.get(Direction.UP)) {
            this.currentDirection = Direction.UP;
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        } else if (d == Direction.RIGHT && availableDirections.get(Direction.RIGHT)) {
            this.currentDirection = Direction.RIGHT;
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.UP);
        } else if (d == Direction.LEFT && availableDirections.get(Direction.LEFT)) {
            this.currentDirection = Direction.LEFT;
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.UP);
            enableDirection(Direction.RIGHT);
        } else if (d == Direction.DOWN && availableDirections.get(Direction.DOWN)) {
            this.currentDirection = Direction.DOWN;
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void disableDirection(Direction d) {
        availableDirections.put(d, false);
    }

    public void enableDirection(Direction d) {
        availableDirections.put(d, true);
    }

    public boolean collidesWith(Entity e) {
        return !(e.getClass() == Floor.class) && this.hitbox.intersects(e.hitbox);
    }

    public ArrayList<Direction> checkCollisionDirectionWith(Entity e) {
        return hitbox.checkCollisionDirection(e.getHitbox());
    }

    public void handleCollisionWith(Entity e) {
        if (e.getClass() == Wall.class && this.collidesWith(e)) {
            ArrayList<Direction> collisionDirections = this.checkCollisionDirectionWith(e);
            if (collisionDirections.contains(Direction.UP)) {
                this.disableDirection(Direction.UP);
                move(Direction.DOWN);
            }
            if (collisionDirections.contains(Direction.DOWN)) {
                this.disableDirection(Direction.DOWN);
                move(Direction.UP);
            }
            if (collisionDirections.contains(Direction.RIGHT)) {
                this.disableDirection(Direction.RIGHT);
                move(Direction.LEFT);
            }
            if (collisionDirections.contains(Direction.LEFT)) {
                this.disableDirection(Direction.LEFT);
                move(Direction.RIGHT);
            }
        }
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

}
