package main.view.game;

import main.model.GameModel;
import main.model.fixedElements.PowerUp;
import main.model.movingElements.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiPanel extends JPanel {
    private ArrayList<Player> players;
    private JPanel centerPanel;
    private String[] playerLabels = {"Orange", "Gray", "Brown"};
    private String[] iconPaths = {"/main/assets/images/gui/icons/iconorange.png", "/main/assets/images/gui/icons/icongray.png", "/main/assets/images/gui/icons/iconbrown.png"};
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

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/main/assets/images/gui/bar.png")));
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
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        JLabel label = new JLabel(text, icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setIconTextGap(5);
        return label;
    }
}
