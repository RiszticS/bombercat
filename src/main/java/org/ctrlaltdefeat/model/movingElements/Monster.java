package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.game.RenderTimer;
import org.ctrlaltdefeat.controllers.graphics.MovingAnimationGraphics;
import org.ctrlaltdefeat.model.Hitbox;
import org.ctrlaltdefeat.model.fixedElements.FixedElement;
import org.ctrlaltdefeat.model.graphics.AnimationConfiguration;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends MovingElement {
    private Direction currentDirection;
    private final Random random;
    protected int speed;
    private final RenderTimer randomDirectionChangeCountdown;

    public Monster(MatrixPosition p) {
        super(p);

        int tileSize = GraphicProperties.getTileSize();
        ArrayList<AnimationConfiguration> animationConfiguration = new ArrayList<>();
        animationConfiguration.add(new AnimationConfiguration("/images/monster.png", 1, 1, 1, 0, 32, 32, 2));
        MovingAnimationGraphics gm = new MovingAnimationGraphics(animationConfiguration, position, 2.0);

        setGraphicsManager(gm);

        this.currentDirection = Direction.randomDirection();
        random = new Random();
        this.speed = 2; /*random.nextInt(4) + 2;*/
        this.hitbox = new Hitbox(position, tileSize / 2, tileSize / 2, tileSize / 4, tileSize / 4);
        randomDirectionChangeCountdown = new RenderTimer(random.nextInt(180, 190));
        randomDirectionChangeCountdown.start();
    }

    /**
     * The update method of the Monster class provides an interface for the GameModel to update the given Monster
     * object. This is the method that should be called inside the GameModel object if the Monster is to be
     * updated.
     * @param board The board, i.e. the FixedElement[][] object of the GameModel that the Monster is a part of.
     */
    public void update(FixedElement[][] board) {
        collisionManager.handleCollisions(this, board);
        changeDirectionRandomly();
        move();
    }

    @Override
    protected void move() {
        if (currentDirection == Direction.UP && !collisionManager.isUpDisabled()) {
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
        } else if (currentDirection == Direction.DOWN && !collisionManager.isDownDisabled()) {
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
        } else if (currentDirection == Direction.LEFT && !collisionManager.isLeftDisabled()) {
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
        } else if (currentDirection == Direction.RIGHT && !collisionManager.isRightDisabled()) {
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
        }
    }

    /**
     * Changes the current direction of the Monster to the direction received as a parameter.
     * @param d  The direction that we want the current direction to change to.
     */
    public void changeDirection(Direction d) {
        this.currentDirection = d;
    }

    private void changeDirectionRandomly() {
        if (randomDirectionChangeCountdown.finished()) {
            changeDirection(Direction.randomDirection(collisionManager.getAvailableDirections()));
            randomDirectionChangeCountdown.set(random.nextInt(180, 190));
            randomDirectionChangeCountdown.start();
        }
        else {
            randomDirectionChangeCountdown.decrease();
        }
    }
}
