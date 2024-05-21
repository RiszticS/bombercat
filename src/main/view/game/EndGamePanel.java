package main.view.game;

import main.controllers.game.GameLoop;
import main.view.menu.MenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EndGamePanel extends JPanel implements ActionListener {
    private final GamePanel gamePanel;
    private final GameLoop gameLoop;
    private final JButton[] buttons;

    public EndGamePanel(ArrayList<Integer> scores, GamePanel gamePanel, GameLoop gameLoop) {
        this.gamePanel = gamePanel;
        this.gameLoop = gameLoop;

        this.setOpaque(false);

        int margin = 15;

        JPanel endPanel = new JPanel();
        endPanel.setLayout(new BorderLayout());
        endPanel.setBackground(new Color(0, 0, 0, 179));

        JLabel titleText = new JLabel("Game over", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin * 5, margin));
        endPanel.add(titleText, BorderLayout.NORTH);

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
        endPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, margin, margin*4));
        String[] buttonLabels = {"Restart", "Main menu"};
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = gamePanel.getGameWindow().createButton(buttonLabels[i], this,
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/button.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonHover.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonPressed.png")));
            buttonPanel.add(buttons[i]);
        }
        endPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(endPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            gamePanel.getGameModel().resetGame(gamePanel);
            this.setVisible(false);
            gameLoop.startGame();
        } else if (e.getSource() == buttons[1]) {
            gamePanel.getGameModel().resetGame(gamePanel);
            gamePanel.getGameWindow().dispose();
            SwingUtilities.invokeLater(() -> new MenuWindow());
        }
    }
}