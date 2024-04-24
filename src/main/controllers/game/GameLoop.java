package main.controllers.game;

import main.model.GameModel;
import main.view.game.GamePanel;


public class GameLoop implements Runnable {
    GameModel gameModel;
    GamePanel gamePanel;
    Thread gameThread;
    private boolean gameRunning;
    private final int refreshRate = 60;

    public GameLoop(GameModel m, GamePanel p) {
        this.gameModel = m;
        this.gamePanel = p;
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

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1 && gameRunning) {
                // 1 UPDATE: update information such as character position.
                gameModel.update(this, gamePanel);
                // 2 DRAW: draw the screen with the updated information.
                gamePanel.repaint();
                delta--;
            }
        }
    }

    public void pauseGame() {
        this.gameRunning = false;
    }

    public void startGame() {
        System.out.println("Hello Hello Hello");
        this.gameRunning = true;
    }
}
