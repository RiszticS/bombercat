package models;

import models.entities.Player;
import models.entities.Wall;

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
                p.handleCollisionWith(w);
            }
        }
    }




}
