package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.controllers.configuration.ControlsProperties;
import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.game.RenderTimer;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.MovingAnimationGraphics;
import org.ctrlaltdefeat.controllers.movement.PlayerControls;
import org.ctrlaltdefeat.model.fixedElements.Bomb;
import org.ctrlaltdefeat.model.fixedElements.FixedElement;
import org.ctrlaltdefeat.model.fixedElements.PowerUp;
import org.ctrlaltdefeat.model.graphics.AnimationConfiguration;
import org.ctrlaltdefeat.model.positions.CoordinatePosition;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends MovingElement implements KeyListener {
    private FixedElement[][] board;
    private MovingAnimationGraphics graphicsManager;
    private final PlayerControls controls;
    private boolean upKeyPressed, downKeyPressed, leftKeyPressed, rightKeyPressed, plantBombKeyPressed;
    private final ArrayList<PowerUp> powerUps;
    private final ArrayDeque<Bomb> bombs;
    private final RenderTimer plantBombCooldown;
    private RenderTimer effectTimer;
    private boolean canPlaceBomb;
    private static int numberOfInstancesCreated;
    private final int id;

    public Player(MatrixPosition p, FixedElement[][] board, int playerIndex) {
        super(p);

        ArrayList<AnimationConfiguration> animationConfiguration = new ArrayList<>();
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "walkback.png", 9, 1, 9, 0, 32, 48, 2));
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "walkright.png", 6, 1, 6, 0, 32, 48, 3));
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "walkfront.png", 9, 1, 9, 0, 32, 48, 2));
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "walkleft.png", 6, 1, 6, 0, 32, 48, 3));
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "idle.png", 13, 1, 13, 0, 32, 48, 3));
        animationConfiguration.add(new AnimationConfiguration("/images/players/astronaut" + playerIndex + "death.png", 13, 1, 13, 0, 32, 48, 6));
        this.graphicsManager = new MovingAnimationGraphics(animationConfiguration, position, 1.3);
        GraphicsController.addManager(this.graphicsManager);

        numberOfInstancesCreated++;
        controls = ControlsProperties.getPlayerControls(playerIndex + 1);

        this.board = board;
        this.upKeyPressed = false;
        this.downKeyPressed = false;
        this.leftKeyPressed = false;
        this.rightKeyPressed = false;
        this.plantBombKeyPressed = false;
        this.powerUps = new ArrayList<>();
        this.effectTimer = new RenderTimer(600);
        this.bombs = new ArrayDeque<>();
        bombs.add(new Bomb(new MatrixPosition(0, 0)));
        plantBombCooldown = new RenderTimer(11);
        this.id = numberOfInstancesCreated;
        this.canPlaceBomb = true;
    }

    public void update(FixedElement[][] board, ArrayList<Monster> monsters) {
        collisionManager.handleCollisions(this, board, monsters);
        move();
        plantBomb(board);
        if (!effectTimer.finished()) {
            effectTimer.decrease();
        } else {
            setPlantBombKeyPressed(false);
        }
        removeExpiredPowerUps();
    }

    private void removeExpiredPowerUps() {
        Iterator<PowerUp> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            if (powerUp.isUsed()) {
                iterator.remove();
            }
        }
    }

    protected void move() {
        if (upKeyPressed && !collisionManager.isUpDisabled()) {
            graphicsManager.changeDirection(Direction.UP);
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
        } else if (downKeyPressed && !collisionManager.isDownDisabled()) {
            graphicsManager.changeDirection(Direction.DOWN);
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
        } else if (leftKeyPressed && !collisionManager.isLeftDisabled()) {
            graphicsManager.changeDirection(Direction.LEFT);
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
        } else if (rightKeyPressed && !collisionManager.isRightDisabled()) {
            graphicsManager.changeDirection(Direction.RIGHT);
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
        } else {
            graphicsManager.changeDirection(Direction.IDLE);
        }
    }

    public void plantBomb(FixedElement[][] board) {
        if (canPlaceBomb && plantBombKeyPressed && plantBombCooldown.finished()) {
            MatrixPosition bombPosition = hitbox.getCentre().convertToMatrixPosition(GraphicProperties.getTileSize());
            if (bombs.peekFirst().isAvailableToPlant() &&
                    board[bombPosition.getX()][bombPosition.getY()].getType().equals("EmptyTile")) {
                Bomb bomb = bombs.removeFirst();
                bomb.plant(bombPosition);
                board[bombPosition.getX()][bombPosition.getY()] = bomb;
                bombs.add(bomb);
                plantBombCooldown.start();
            }
        } else if (!plantBombCooldown.finished()) {
            plantBombCooldown.decrease();
        }
    }

    public void pickUpPowerUp(PowerUp p) {
        this.powerUps.add(p);
        p.apply(this);
        p.setPickedUp(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == controls.getUp()) {
            upKeyPressed = true;
        } else if (e.getKeyCode() == controls.getDown()) {
            downKeyPressed = true;
        } else if (e.getKeyCode() == controls.getLeft()) {
            leftKeyPressed = true;
        } else if (e.getKeyCode() == controls.getRight()) {
            rightKeyPressed = true;
        } else if (e.getKeyCode() == controls.getBomb()) {
            plantBombKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == controls.getUp()) {
            upKeyPressed = false;
        } else if (e.getKeyCode() == controls.getDown()) {
            downKeyPressed = false;
        } else if (e.getKeyCode() == controls.getLeft()) {
            leftKeyPressed = false;
        } else if (e.getKeyCode() == controls.getRight()) {
            rightKeyPressed = false;
        } else if (e.getKeyCode() == controls.getBomb()) {
            plantBombKeyPressed = false;
        }
    }

    public static void resetNumberOfInstancesCreated() {
        numberOfInstancesCreated = 0;
    }

    public void setCanPlaceBomb(boolean canPlaceBomb) {
        this.canPlaceBomb = canPlaceBomb;
    }

    public void setPlantBombKeyPressed(boolean plantBombKeyPressed) {
        this.plantBombKeyPressed = plantBombKeyPressed;
    }

    public int getId() {
        return id;
    }

    public ArrayDeque<Bomb> getBombs() {
        return bombs;
    }

    public FixedElement[][] getBoard() {
        return board;
    }

    public ArrayList<PowerUp> getPowerUp() {
        return powerUps;
    }

    public RenderTimer getEffectTimer() {
        return effectTimer;
    }

    public MovingAnimationGraphics getMovingAnimationGraphics() {
        return graphicsManager;
    }
}

