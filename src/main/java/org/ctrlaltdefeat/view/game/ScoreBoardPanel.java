package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.controllers.game.GameLoop;
import org.ctrlaltdefeat.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScoreBoardPanel extends JPanel {
    private final GameLoop gameLoop;
    private final GameModel gameModel;
    private final GamePanel gamePanel;

    public ScoreBoardPanel(ArrayList<Integer> scores, GameLoop gameLoop, GameModel gameModel, GamePanel gamePanel) {
        this.gameLoop = gameLoop;
        this.gameModel = gameModel;
        this.gamePanel = gamePanel;

        this.setOpaque(false);

        int margin = 15;

        JPanel pausePanel = new JPanel();
        pausePanel.setLayout(new BorderLayout());
        pausePanel.setBackground(new Color(0, 0, 0, 179));

        JLabel titleText = new JLabel("Score board", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin * 5, margin));
        pausePanel.add(titleText, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        String[] playerLabels = {"Orange", "Gray", "Brown"};
        for (int i = 0; i < scores.size(); i++) {
            JLabel player = new JLabel(playerLabels[i] + " cat: " + scores.get(i) + " points");
            player.setFont(new Font("Press Start 2P", Font.BOLD, 18));
            player.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createRigidArea(new Dimension(0, margin * 3)));
            player.setForeground(Color.WHITE);
            centerPanel.add(player);
        }
        pausePanel.add(centerPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(pausePanel);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextRound();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void nextRound() {
        this.setVisible(false);
        gameModel.resetLevel(gamePanel);
        gameLoop.startGame();
    }
}
