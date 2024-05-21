package org.ctrlaltdefeat.model;

import org.ctrlaltdefeat.controllers.game.GameLoop;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.view.game.EndGamePanel;
import org.ctrlaltdefeat.view.game.GamePanel;
import org.ctrlaltdefeat.view.game.ScoreBoardPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private int levelNumber, playerNumber, numberOfWinsNecessary;
    private boolean createdLevel;
    private Level currentLevel;
    private final ArrayList<Integer> scores;
    private ScoreBoardPanel scoreBoardPanel;
    private EndGamePanel endGamePanel;

    public GameModel(int levelNumber, int playerNumber, int numberOfWinsNecessary, boolean createdLevel) {
        this.levelNumber = levelNumber;
        this.playerNumber = playerNumber;
        this.numberOfWinsNecessary = numberOfWinsNecessary;
        this.createdLevel = createdLevel;
        scores = new ArrayList<>();
        for (int i = 0; i < playerNumber; i++) {
            scores.add(0);
        }
        try {
            currentLevel = new Level(levelNumber, playerNumber, createdLevel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GameLoop gameLoop, GamePanel gamePanel) {
        if (!scores.contains(numberOfWinsNecessary)) {
            if (currentLevel.isDraw()) {
                gameLoop.pauseGame();
                addScoreBoardPanel(gameLoop, gamePanel);
            } else if (currentLevel.isWin()) {
                gameLoop.pauseGame();
                updateScores();
                if (!scores.contains(numberOfWinsNecessary)) {
                    addScoreBoardPanel(gameLoop, gamePanel);
                } else if (scores.contains(numberOfWinsNecessary)) {
                    addEndGamePanel(gamePanel, gameLoop);
                }
            } else {
                currentLevel.update();
                gamePanel.getGameWindow().getGuiPanel().setPlayers(currentLevel.getPlayers());
            }
        }
    }

    public void addScoreBoardPanel(GameLoop gameLoop, GamePanel gamePanel) {
        scoreBoardPanel = new ScoreBoardPanel(scores, gameLoop, this, gamePanel);
        scoreBoardPanel.setVisible(true);
        gamePanel.getGameWindow().addPanel(scoreBoardPanel, Integer.valueOf(1));
    }

    public void addEndGamePanel(GamePanel gamePanel, GameLoop gameLoop) {
        endGamePanel = new EndGamePanel(scores, gamePanel, gameLoop);
        endGamePanel.setVisible(true);
        gamePanel.getGameWindow().addPanel(endGamePanel, Integer.valueOf(1));
    }

    public void resetLevel(GamePanel gamePanel) {
        try {
            gamePanel.removeKeyListenersForPlayers();
            Player.resetNumberOfInstancesCreated();
            GraphicsController.reset();
            this.currentLevel = new Level(this.levelNumber, this.playerNumber, this.createdLevel);
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

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void resetGame(GamePanel gamePanel) {
        for (int i = 0; i < playerNumber; i++) {
            scores.set(i, 0);
        }
        resetLevel(gamePanel);
    }
}
