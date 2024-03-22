package model;

import controller.ControlSet;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private final ArrayList<Player> players;
    private Level currentLevel;

    public GameModel(int levelNumber) {

        try {
            this.currentLevel = new Level(levelNumber);
            this.players = this.currentLevel.getPlayers();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void checkCollisions() {
        for (Player p : players) {
            for (Wall w : this.currentLevel.getWallTiles()) {
                if (p.collidesWith(w)) {
                    ArrayList<Direction> collisionDirections = p.checkCollisionDirectionWith(w);
                    if (collisionDirections.contains(Direction.UP)) {
                        p.disableDirection(Direction.UP);
                    } if (collisionDirections.contains(Direction.DOWN)) {
                        p.disableDirection(Direction.DOWN);
                    } if (collisionDirections.contains(Direction.RIGHT)) {
                        p.disableDirection(Direction.RIGHT);
                    } if (collisionDirections.contains(Direction.LEFT)) {
                        p.disableDirection(Direction.LEFT);
                    }
                }
            }
        }
    }




}
