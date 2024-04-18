package main.models;

import main.models.entities.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private final ArrayList<Player> players;
    private final ArrayList<Monster> monsters;
    private ArrayList<PowerUp> powerUps;
    private Level currentLevel;

    public GameModel(int levelNumber) {

        try {
            this.currentLevel = new Level(levelNumber);
            this.players = this.currentLevel.getPlayers();
            this.monsters = this.currentLevel.getMonsters();
            this.powerUps = this.currentLevel.getPowerUps();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void checkCollisions() {
        for (Player p : players) {
            for (Wall w : this.currentLevel.getWallTiles()) {
                p.handleCollisionWith(w);
            }
            for (PowerUp pu : this.currentLevel.getPowerUps()){
                p.handleCollisionWithPowerUps(pu);
            }

            for (Chest c : this.currentLevel.getChestTiles()) {
                p.handleCollisionWith(c);
            }
        }
        for (Monster m : monsters) {
            for (Wall w : this.currentLevel.getWallTiles()) {
                m.handleCollisionWith(w);
            }

        }
    }

}
