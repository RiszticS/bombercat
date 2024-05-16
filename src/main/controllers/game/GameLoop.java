package main.controllers.game;

import main.model.GameModel;
import main.view.game.GamePanel;
import main.view.game.GameWindow;


public class GameLoop implements Runnable {
    private final GameModel gameModel;
    private final GameWindow gameWindow;
    Thread gameThread;
    private boolean gameRunning;
    private final int refreshRate = 60;

    public GameLoop(GameModel gameModel, GameWindow gameWindow) {
        this.gameModel = gameModel;
        this.gameWindow = gameWindow;
        this.gameRunning = true;
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // GAME LOOP
        double drawInterval = 1000000000 / refreshRate;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;


            if (delta >= 1) {
                if (gameRunning&&!gameWindow.getIsPaused()) {
                    gameModel.update(this, gameWindow.getGamePanel());
                    gameWindow.getGamePanel().repaint();
                }

                delta--;
            }
        }
    }

    public void pauseGame() {
        this.gameRunning = false;
    }

    public void startGame() {
        this.gameRunning = true;
    }
}
