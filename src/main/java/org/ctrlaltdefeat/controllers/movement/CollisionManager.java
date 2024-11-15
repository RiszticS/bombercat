package org.ctrlaltdefeat.controllers.movement;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.model.fixedElements.FixedElement;
import org.ctrlaltdefeat.model.fixedElements.PowerUp;
import org.ctrlaltdefeat.model.movingElements.Monster;
import org.ctrlaltdefeat.model.movingElements.MovingElement;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.util.ArrayList;

public class CollisionManager {
    private final MovingElement elementToBeMonitored;
    private boolean upDisabled, downDisabled, leftDisabled, rightDisabled;

    public CollisionManager(MovingElement e) {
        this.elementToBeMonitored = e;
        upDisabled = false;
        downDisabled = false;
        leftDisabled = false;
        rightDisabled = false;
    }

    /**
     * Returns an ArrayList<> that contains the tiles around the MovingElement in the matrix that represents the board,
     * and also the tile that the MovingElement itself is standing on.
     * @param board The matrix that represents the board.
     * @return ArrayList
     */
    private ArrayList<FixedElement> getOwnAndAdjacentTiles(FixedElement[][] board) {
        MatrixPosition playerPositionInMatrix = elementToBeMonitored.getHitbox().getCentre().convertToMatrixPosition(GraphicProperties.getTileSize());

        ArrayList<FixedElement> tiles = new ArrayList<>();

        if (playerPositionInMatrix.getX() >= 0 && playerPositionInMatrix.getX() < board.length &&
                playerPositionInMatrix.getY() >= 0 && playerPositionInMatrix.getY() < board.length) {
            tiles.add(board[playerPositionInMatrix.getX()][playerPositionInMatrix.getY()]);
        }
        if (playerPositionInMatrix.getX()-1 >= 0) {
            tiles.add(board[playerPositionInMatrix.getX()-1][playerPositionInMatrix.getY()]);
        }
        if (playerPositionInMatrix.getX()+1 < board.length) {
            tiles.add(board[playerPositionInMatrix.getX()+1][playerPositionInMatrix.getY()]);
        }
        if (playerPositionInMatrix.getY()-1 >= 0) {
            tiles.add(board[playerPositionInMatrix.getX()][playerPositionInMatrix.getY()-1]);
        }
        if (playerPositionInMatrix.getY()+1 < board.length) {
            tiles.add(board[playerPositionInMatrix.getX()][playerPositionInMatrix.getY()+1]);
        }

        return tiles;
    }

    /**
     * This method is polymorphic: it can either be called with a (1) Player type object or with a (2) Monster type
     * object. This is the one related to Player type objects. The main function of the method is to handle the
     * collisions of Player type objects with other objects that have hitboxes. Note: Since Monster type objects can
     * collide with Players, and Players can also collide with Monsters, the collision of two such objects is only
     * checked and handled once, in the current method.
     * @param p The Player type objects to be checked for collisions.
     * @param board The board, i.e. the FixedElement[][] object of the GameModel that the Player is a part of.
     * @param monsters An ArrayList type collection containing the Monster objects of the GameBoard.
     */
    public void handleCollisions(Player p, FixedElement[][] board, ArrayList<Monster> monsters) {
        for (Monster monster : monsters) {
            if (p.getHitbox().intersects(monster.getHitbox())) {
                p.die();
                break;
            }
        }

        ArrayList<FixedElement> elementsToBeChecked = getOwnAndAdjacentTiles(board);
        boolean upperCollisionFound = false;
        boolean lowerCollisionFound = false;
        boolean leftCollisionFound = false;
        boolean rightCollisionFound = false;

        for (FixedElement tile : elementsToBeChecked) {
            if (tile.getType().equals("Chest") || tile.getType().equals("Wall") ||
                    (tile.getType().equals("Bomb") && tile.getPosition() != p.getHitbox().getCentre().convertToMatrixPosition(GraphicProperties.getTileSize()))) {
                if (p.getHitbox().intersectsFromBelow(tile.getHitbox())) {
                    upperCollisionFound = true;
                }
                if (p.getHitbox().intersectsFromAbove(tile.getHitbox())) {
                    lowerCollisionFound = true;
                }
                if (p.getHitbox().intersectsFromRight(tile.getHitbox())) {
                    leftCollisionFound = true;
                }
                if (p.getHitbox().intersectsFromLeft(tile.getHitbox())) {
                    rightCollisionFound = true;
                }
            } else if (tile.getType().equals("Explosion") || tile.getType().equals("Flare")) {
                if (p.getHitbox().intersects(tile.getHitbox()) ||
                    p.getHitbox().getCentre().convertToMatrixPosition(GraphicProperties.getTileSize()).equals(tile.getPosition())
                ) {
                    p.die();
                }
            } else if (tile.getType().equals("PowerUp")) {
                if (p.getHitbox().intersects(tile.getHitbox())) {
                    p.pickUpPowerUp((PowerUp) tile);
                }
            }
        }

        collisionAction(p, Direction.UP, upperCollisionFound);
        collisionAction(p, Direction.DOWN, lowerCollisionFound);
        collisionAction(p, Direction.LEFT, leftCollisionFound);
        collisionAction(p, Direction.RIGHT, rightCollisionFound);
    }

    public void handleCollisions(Monster m, FixedElement[][] board) {
        ArrayList<FixedElement> elementsToBeChecked = getOwnAndAdjacentTiles(board);
        boolean upperCollisionFound = false;
        boolean lowerCollisionFound = false;
        boolean leftCollisionFound = false;
        boolean rightCollisionFound = false;

        for (FixedElement tile : elementsToBeChecked) {
            if (tile.getType().equals("Chest") || tile.getType().equals("Wall") || tile.getType().equals("Bomb")) {
                if (m.getHitbox().intersectsFromBelow(tile.getHitbox())) {
                    upperCollisionFound = true;
                }
                if (m.getHitbox().intersectsFromAbove(tile.getHitbox())) {
                    lowerCollisionFound = true;
                }
                if (m.getHitbox().intersectsFromRight(tile.getHitbox())) {
                    leftCollisionFound = true;
                }
                if (m.getHitbox().intersectsFromLeft(tile.getHitbox())) {
                    rightCollisionFound = true;
                }
            } else if (tile.getType().equals("Flare") || tile.getType().equals("Explosion")) {
                if (m.getHitbox().intersects(tile.getHitbox())) {
                    m.die();
                }
            }
        }

        collisionAction(m, Direction.UP, upperCollisionFound, this.upDisabled);
        collisionAction(m, Direction.DOWN, lowerCollisionFound, this.downDisabled);
        collisionAction(m, Direction.LEFT, leftCollisionFound, this.leftDisabled);
        collisionAction(m, Direction.RIGHT, rightCollisionFound, this.rightDisabled);

    }

    private void collisionAction(Player p, Direction d, boolean collisionFound) {
        if (collisionFound) {
            disableDirection(d);
        } else {
            enableDirection(d);
        }
    }

    private void collisionAction(Monster m, Direction d, boolean collisionFound, boolean directionAlreadyDisabled) {
        if (collisionFound && !directionAlreadyDisabled) {
            disableDirection(d);
            m.changeDirection(Direction.randomDirection(getAvailableDirections()));
        } else if (!collisionFound && directionAlreadyDisabled) {
            enableDirection(d);
        }
    }

    private void disableDirection(Direction d) {
        if (d == Direction.UP) {
            upDisabled = true;
        } else if (d == Direction.DOWN) {
            downDisabled = true;
        } else if (d == Direction.LEFT) {
            leftDisabled = true;
        } else if (d == Direction.RIGHT) {
            rightDisabled = true;
        }
    }

    private void enableDirection(Direction d) {
        if (d == Direction.UP) {
            upDisabled = false;
        } else if (d == Direction.DOWN) {
            downDisabled = false;
        } else if (d == Direction.LEFT) {
            leftDisabled = false;
        } else if (d == Direction.RIGHT) {
            rightDisabled = false;
        }
    }

    /**
     * Returns as an ArrayList the available directions that the elementToBeMonitored can move in at a particular state.
     * @return ArrayList of available directions.
     */
    public ArrayList<Direction> getAvailableDirections() {
        ArrayList<Direction> availableDirections = new ArrayList<>();
        if (!upDisabled) {
            availableDirections.add(Direction.UP);
        }
        if (!downDisabled) {
            availableDirections.add(Direction.DOWN);
        }
        if (!leftDisabled) {
            availableDirections.add(Direction.LEFT);
        }
        if (!rightDisabled) {
            availableDirections.add(Direction.RIGHT);
        }
        return availableDirections;
    }

    public boolean isUpDisabled() {
        return upDisabled;
    }

    public boolean isDownDisabled() {
        return downDisabled;
    }

    public boolean isLeftDisabled() {
        return leftDisabled;
    }

    public boolean isRightDisabled() {
        return rightDisabled;
    }
}
