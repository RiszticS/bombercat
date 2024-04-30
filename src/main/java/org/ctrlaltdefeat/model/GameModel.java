package org.ctrlaltdefeat.model;

import org.ctrlaltdefeat.controllers.game.GameLoop;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.view.game.EndGameWindow;
import org.ctrlaltdefeat.view.game.GamePanel;
import org.ctrlaltdefeat.view.game.ScoreBoardWindow;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private int levelNumber, playerNumber, numberOfWinsNecessary;
    private Level currentLevel;
    private final ArrayList<Integer> scores;


    public GameModel(int levelNumber, int playerNumber, int numberOfWinsNecessary) {
        this.levelNumber = levelNumber;
        this.playerNumber = playerNumber;
        this.numberOfWinsNecessary = numberOfWinsNecessary;
        scores = new ArrayList<>();
        for (int i = 0; i < playerNumber; i++) {
            scores.add(0);
        }
        try {
            this.currentLevel = new Level(levelNumber, playerNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GameLoop gameLoop, GamePanel gamePanel) {
        if (!scores.contains(numberOfWinsNecessary)) {
            if (currentLevel.isDraw()) {
                gameLoop.pauseGame();
                new ScoreBoardWindow(scores, gameLoop, this, gamePanel);
            } else if (currentLevel.isWin()) {
                gameLoop.pauseGame();
                updateScores();
                if (!scores.contains(numberOfWinsNecessary)) {
                    new ScoreBoardWindow(scores, gameLoop, this, gamePanel);
                } else if (scores.contains(numberOfWinsNecessary)) {
                    new EndGameWindow(scores);
                }

            } else {
                currentLevel.update();
            }
        }
    }

    public void resetLevel(GamePanel gamePanel) {
        try {
            gamePanel.removeKeyListenersForPlayers();
            Player.resetNumberOfInstancesCreated();
            GraphicsController.reset();
            this.currentLevel = new Level(this.levelNumber, this.playerNumber);
            gamePanel.addKeyListenersForPlayers();
        } catch (IOException e) {
            System.out.println("The level could not be reset!");
        }
    }

    private void updateScores() {
        int winnerId = currentLevel.getWinner().getId() - 1;
        int newScore = scores.get(winnerId) + 1;
        scores.set(winnerId, newScore);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
