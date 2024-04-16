package views.game;

import controllers.configuration.GraphicProperties;
import controllers.graphics.GraphicsController;
import controllers.movement.ControlSet;
import controllers.movement.PlayerController;
import models.GameModel;
import models.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    GameModel model;
    private final ArrayList<PlayerController> playerControllers;

    public GamePanel(GameModel m) {
        int tileSize = GraphicProperties.getTileSize();
        int numberOfColumns = GraphicProperties.getColNumber();
        int numberOfRows = GraphicProperties.getRowNumber();;
        int panelWidth = numberOfColumns * tileSize;
        int panelHeight = numberOfRows * tileSize;

        this.model = m;
        this.playerControllers = new ArrayList<>();

        int i = 0;
        for (Player player : model.getPlayers()) {
            PlayerController pc = new PlayerController(player, ControlSet.values()[i]);
            playerControllers.add(pc);
            this.addKeyListener(pc);
            i++;
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

    public ArrayList<PlayerController> getPlayerControllers() {
        return playerControllers;
    }
}
