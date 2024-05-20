package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.model.GameModel;

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
        this.setIconImage(new ImageIcon(getClass().getResource("/images/gui/icon.png")).getImage());
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

}