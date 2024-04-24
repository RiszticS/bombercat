package main.model;

import main.controllers.game.GameLoop;
import main.model.fixedElements.PowerUp;
import main.model.movingElements.Monster;
import main.model.movingElements.Player;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private int levelNumber, playerNumber;
    private Level currentLevel;

    public GameModel(int levelNumber, int playerNumber) {
        this.levelNumber = levelNumber;
        this.playerNumber = playerNumber;
        try {
            this.currentLevel = new Level(levelNumber, playerNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GameLoop loop) {
        if (currentLevel.isDraw()) {
            // stop thread and do sg
        } else if (currentLevel.isWin()) {
            // stop thread and do sg
        } else {
            currentLevel.update();
        }
    }

    private void resetLevel() {
        try {
            currentLevel = new Level(this.levelNumber, this.playerNumber);
        } catch (IOException e) {
            System.out.println("The level could not be reset!");
        }

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
