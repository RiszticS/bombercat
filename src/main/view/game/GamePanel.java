package main.view.game;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.model.GameModel;
import main.model.movingElements.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    GameModel model;

    public GamePanel(GameModel gameModel) {
        this.model = gameModel;
        int tileSize = GraphicProperties.getTileSize();
        int numberOfColumns = GraphicProperties.getColNumber();
        int numberOfRows = GraphicProperties.getRowNumber();;
        int panelWidth = numberOfColumns * tileSize;
        int panelHeight = numberOfRows * tileSize;
        for (Player p : model.getCurrentLevel().getPlayers()) {
            this.addKeyListener(p);
        }
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GraphicsController.draw(g2);
        g2.dispose();
    }
}
