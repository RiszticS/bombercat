package models.entities;

import controllers.graphics.AnimationConfiguration;
import controllers.graphics.MovingAnimationGraphics;
import models.Direction;
import models.Movable;
import models.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Entity implements Movable {
    private final int speed = 4;
    private int boardX;
    private int boardY;
    private final Hitbox hitbox;
    private HashMap<Direction, Boolean> availableDirections;
    private Direction previousDirection;
    private final MovingAnimationGraphics graphicsManager;

    public Player(int x, int y) {
        this.position = new Position(x * 64, y * 64);
        this.boardX = x;
        this.boardY = y;
        this.hitbox = new Hitbox(this.position.getX() + 5, this.position.getY() + 36, 54, 54);

        ArrayList<AnimationConfiguration> animationConfiguration = new ArrayList<>();
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkback.png", 9, 1, 9, 0, 32, 48, 5));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkright.png", 6, 1, 6, 0, 32, 48, 6));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkfront.png", 9, 1, 9, 0, 32, 48, 5));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkleft.png", 6, 1, 6, 0, 32, 48, 6));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautidle.png", 13, 1, 13, 0, 32, 48, 5));
        this.graphicsManager = new MovingAnimationGraphics(animationConfiguration);

        this.availableDirections = new HashMap<>();
        availableDirections.put(Direction.UP, true);
        availableDirections.put(Direction.DOWN, true);
        availableDirections.put(Direction.LEFT, true);
        availableDirections.put(Direction.RIGHT, true);

        this.previousDirection = Direction.IDLE;
        graphicsManager.changeDirection(Direction.IDLE);
    }


    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void move(Direction d) {
        if (d == Direction.UP && availableDirections.get(Direction.UP) ) {
            graphicsManager.changeDirection(d);
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.RIGHT && availableDirections.get(Direction.RIGHT)) {
            graphicsManager.changeDirection(d);
            this.previousDirection = Direction.RIGHT;
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.UP);
        }
        else if (d == Direction.LEFT && availableDirections.get(Direction.LEFT)) {
            graphicsManager.changeDirection(d);
            this.previousDirection = Direction.LEFT;
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.UP);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.DOWN && availableDirections.get(Direction.DOWN)) {
            graphicsManager.changeDirection(d);
            this.previousDirection = Direction.DOWN;
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        } else {
            graphicsManager.changeDirection(Direction.IDLE);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
            enableDirection(Direction.DOWN);
        }
    }

    private Direction copyDirection(Direction d) {
        return switch (d) {
            case UP -> Direction.UP;
            case RIGHT -> Direction.RIGHT;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            default -> Direction.IDLE;
        };
    }
    public void changeBoardPosition(int x, int y) {
        this.boardX = x;
        this.boardY = y;
    }

    public void placeBomb() {

    }

    @Override
    public void draw(Graphics2D g2) {
        graphicsManager.draw(g2, this.position.getX(), this.position.getY());
        //hitbox.draw(g2);
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
        if ((e.getClass() == Wall.class || e.getClass() == Chest.class) && this.collidesWith(e)) {
            ArrayList<Direction> collisionDirections = this.checkCollisionDirectionWith(e);
            if (collisionDirections.contains(Direction.UP)) {
                this.disableDirection(Direction.UP);
            } if (collisionDirections.contains(Direction.DOWN)) {
                this.disableDirection(Direction.DOWN);
            } if (collisionDirections.contains(Direction.RIGHT)) {
                this.disableDirection(Direction.RIGHT);
            } if (collisionDirections.contains(Direction.LEFT)) {
                this.disableDirection(Direction.LEFT);
            }
        }
    }

}