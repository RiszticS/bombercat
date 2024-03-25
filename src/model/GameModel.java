package model;

import controller.ControlSet;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private final ArrayList<Player> players;
    private ArrayList<PowerUp> powerUps;
    private Level currentLevel;

    public GameModel(int levelNumber) {

        try {
            this.currentLevel = new Level(levelNumber);
            this.players = this.currentLevel.getPlayers();
            this.powerUps = this.currentLevel.getPowerUps();
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
            for (PowerUp pu : this.currentLevel.getPowerUps()){
                p.handleCollisionWithPowerUps(pu);
            }
        }
    }




}
