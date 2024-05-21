package main.view.game;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.model.GameModel;
import main.model.movingElements.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameModel gameModel;
    private final GameWindow gameWindow;

    public GamePanel(GameModel gameModel,GameWindow gameWindow) {
        this.gameModel = gameModel;
        this.gameWindow=gameWindow;
        addKeyListenersForPlayers();
        this.setPreferredSize(getGameSize());
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GraphicsController.draw(g2);
        g2.dispose();
    }

    public void addKeyListenersForPlayers() {
        for (Player p : gameModel.getCurrentLevel().getPlayers()) {
            this.addKeyListener(p);
        }
    }

    public void removeKeyListenersForPlayers() {
        for (Player p : gameModel.getCurrentLevel().getPlayers()) {
            this.removeKeyListener(p);
        }
    }

    public Dimension getGameSize(){
        int tileSize = GraphicProperties.getTileSize();
        int numberOfColumns = GraphicProperties.getColNumber();
        int numberOfRows = GraphicProperties.getRowNumber();
        int panelWidth = numberOfColumns * tileSize;
        int panelHeight = numberOfRows * tileSize;
        return new Dimension(panelWidth,panelHeight);
    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }

    public GameModel getGameModel(){
        return gameModel;
    }
}
