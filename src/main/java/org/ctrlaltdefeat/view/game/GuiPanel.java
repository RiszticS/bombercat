package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.model.GameModel;
import org.ctrlaltdefeat.model.fixedElements.PowerUp;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.view.menu.LevelSelector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiPanel extends JPanel {
    private ArrayList<Player> players;
    private JPanel centerPanel;
    private String[] playerLabels = {"Orange", "Gray", "Brown"};
    private String[] iconPaths = {"/images/gui/icons/iconorange.png", "/images/gui/icons/icongray.png", "/images/gui/icons/iconbrown.png"};
    private Map<Integer, JLabel> playerLabelsMap;

    public GuiPanel(GameModel gameModel) {
        this.players = gameModel.getCurrentLevel().getPlayers();
        this.playerLabelsMap = new HashMap<>();
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(900, 100));
        int margin = 15;

        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, margin * 2, 0));

        initPlayerLabels();

        JLabel background=null;
        try {
            background = new JLabel(new ImageIcon(ImageIO.read(GuiPanel.class.getResourceAsStream("/images/gui/bar.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(new BorderLayout());
        this.add(background, BorderLayout.CENTER);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(centerPanel);
    }

    public void initPlayerLabels() {
        centerPanel.removeAll();
        playerLabelsMap.clear();

        for (Player player : players) {
            int playerId = player.getId() - 1;
            if (playerId >= 0 && playerId < playerLabels.length) {
                JLabel label = createLabelWithIcon(playerLabels[playerId] + " cat: ", iconPaths[playerId]);
                label.setName("playerLabel" + playerId);
                label.setFont(new Font("Press Start 2P", Font.BOLD, 10));
                label.setForeground(Color.WHITE);
                centerPanel.add(label);
                playerLabelsMap.put(playerId, label);
            }
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        updatePlayerLabels();
    }

    private void updatePlayerLabels() {
        ArrayList<Integer> alivePlayerIds = new ArrayList<>();
        for (Player player : players) {
            alivePlayerIds.add(player.getId() - 1);
        }

        playerLabelsMap.keySet().removeIf(playerId -> !alivePlayerIds.contains(playerId));
        centerPanel.removeAll();

        for (Player player : players) {
            int playerId = player.getId() - 1;
            StringBuilder labelText = new StringBuilder(playerLabels[playerId] + " cat: ");

            for (PowerUp powerUp : player.getPowerUp()) {
                String powerUpName = powerUp.getClass().getSimpleName().replace("PowerUp", "");
                labelText.append(powerUpName).append(" ");
            }

            JLabel playerLabel = playerLabelsMap.get(playerId);
            if (playerLabel == null) {
                playerLabel = createLabelWithIcon(labelText.toString(), iconPaths[playerId]);
                playerLabel.setName("playerLabel" + playerId);
                playerLabel.setFont(new Font("Press Start 2P", Font.BOLD, 10));
                playerLabel.setForeground(Color.WHITE);
                playerLabelsMap.put(playerId, playerLabel);
            } else {
                playerLabel.setText(labelText.toString());
            }
            centerPanel.add(playerLabel);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JLabel createLabelWithIcon(String text, String iconPath) {
        ImageIcon icon = null;
        try {
            URL resource = GuiPanel.class.getResource(iconPath);
            if (resource == null) {
                System.err.println("Resource not found: " + iconPath);
            } else {
                icon = new ImageIcon(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel(text, icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setIconTextGap(5);
        return label;
    }

}
