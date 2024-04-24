package main.model;

import main.controllers.game.GameLoop;
import main.controllers.graphics.GraphicsController;
import main.model.fixedElements.PowerUp;
import main.model.movingElements.Monster;
import main.model.movingElements.Player;
import main.view.game.GamePanel;
import main.view.game.ScoreBoardWindow;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameModel {
    private int levelNumber, playerNumber, numberOfRounds, currentRound;
    private Level currentLevel;
    private final ArrayList<Integer> scores;

    public GameModel(int levelNumber, int playerNumber, int numberOfRounds) {
        this.levelNumber = levelNumber;
        this.playerNumber = playerNumber;
        this.numberOfRounds = numberOfRounds;
        this.currentRound = 1;
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
        if (currentRound <= numberOfRounds) {
            if (currentLevel.isDraw()) {
                // stop thread and do sg
                //currentRound++;
            } else if (currentLevel.isWin()) {
                // stop thread and do sg
                gameLoop.pauseGame();
                updateScores();
                new ScoreBoardWindow(scores, gameLoop, this, gamePanel);
                //resetLevel(gamePanel);
                //currentRound++;
            } else {
                currentLevel.update();
            }
        } else {
            // Stop GameLoop thread
            // Print final score table
            System.out.println("Game ended.");
            for (Integer i : scores) {
                System.out.println("Player: " + i);
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
        //if winner != null
        int winnerId = currentLevel.getWinner().getId() - 1;
        int newScore = scores.get(winnerId) + 1;
        scores.set(winnerId, newScore);
    }

    public void nextRound() {
        this.currentRound++;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
