package main.controllers.game;

import main.controllers.movement.PlayerController;
import main.models.GameModel;
import main.models.entities.Monster;
import main.view.game.GamePanel;


public class GameLoop implements Runnable {
    GameModel gameModel;
    GamePanel gamePanel;
    Thread gameThread;
    private final int refreshRate = 60;

    public GameLoop(GameModel m, GamePanel p) {
        this.gameModel = m;
        this.gamePanel = p;
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

            if (delta >= 1) {
                // 1 UPDATE: update information such as character position.
                updateModel();
                // 2 DRAW: draw the screen with the updated information.
                repaintScreen();
                delta--;
            }

        }
    }

    public void updateModel() {
        this.gameModel.checkCollisions();
        for (PlayerController pc : gamePanel.getPlayerControllers()) {
            pc.updatePlayer();
        }
        for(Monster m : gameModel.getMonsters()){
            m.move(m.getCurrentDirection());
        }
    }

    public void repaintScreen() {
        gamePanel.repaint();
    }

}
