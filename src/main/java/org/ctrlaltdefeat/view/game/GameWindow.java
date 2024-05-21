package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.model.GameModel;
import org.ctrlaltdefeat.view.menu.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

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
        try {
            this.setIconImage(new ImageIcon(ImageIO.read(GameWindow.class.getResourceAsStream("/images/gui/icon.png"))).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

}
