package main.view.game;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.model.GameModel;
import main.model.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    GameModel model;

    public GamePanel(GameModel gameModel) {
        this.model = gameModel;
        int tileSize = GraphicProperties.getTileSize();
        int numberOfColumns = GraphicProperties.getColNumber();
        int numberOfRows = GraphicProperties.getRowNumber();;
        int panelWidth = numberOfColumns * tileSize;
        int panelHeight = numberOfRows * tileSize;
        for (Player p : model.getPlayers()) {
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

        model.getCurrentLevel().draw(g2);

        GraphicsController.draw(g2);
        // Dispose of this graphic context and release any system resources that it is using.
        g2.dispose();
    }
}
