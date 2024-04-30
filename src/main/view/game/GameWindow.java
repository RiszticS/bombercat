package main.view.game;

import main.model.GameModel;

import javax.swing.*;

public class GameWindow extends JFrame {

    private final GamePanel gamePanel;
    public GameWindow(GameModel m) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Bomberman");
        this.gamePanel = new GamePanel(m);
        this.getContentPane().add(gamePanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

}
