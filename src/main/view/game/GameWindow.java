package main.view.game;

import main.controllers.configuration.GraphicProperties;
import main.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {
    private final GamePanel gamePanel;
    private final PausePanel pausePanel;

    private boolean isPaused;

    public GameWindow(GameModel gameModel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Bomberman");
        isPaused = false;

        gamePanel = new GamePanel(gameModel);
        pausePanel = new PausePanel(this);
        pausePanel.setVisible(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
            layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(pausePanel, JLayeredPane.PALETTE_LAYER);

        getContentPane().add(layeredPane);
        pack();
        setPreferredSize(gamePanel.getWindowSize());
        setVisible(true);
        setLocationRelativeTo(null);
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        KeyStroke escKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action escAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKeyStroke, "PauseGame");
        getRootPane().getActionMap().put("PauseGame", escAction);
    }

    private void togglePause() {
        isPaused = !isPaused;
        pausePanel.setVisible(isPaused);
        if (!getIsPaused()) {
            gamePanel.requestFocusInWindow();
        }
    }

    public boolean getIsPaused(){
        return isPaused;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }
}
