package main.model;

import main.model.fixedElements.PowerUp;
import main.model.movingElements.Monster;
import main.model.movingElements.Player;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private Level currentLevel;

    public GameModel(int levelNumber, int playerNumber) {
        try {
            this.currentLevel = new Level(levelNumber, playerNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        currentLevel.update();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
