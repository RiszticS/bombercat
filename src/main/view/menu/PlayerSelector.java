package main.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelector extends JPanel implements ActionListener {
    private final MenuWindow menuWindow;
    private final JButton[] playerButtons;
    private final JButton[] navigationButtons;
    private int playerNumber = 0;

    public PlayerSelector(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        int margin = 15;

        JPanel playerSelectorPanel = new JPanel();
        playerSelectorPanel.setLayout(new BorderLayout());
        playerSelectorPanel.setOpaque(false);

        JLabel titleText = new JLabel("Player selector", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin, margin));
        playerSelectorPanel.add(BorderLayout.NORTH, titleText);

        playerButtons = new JButton[3];
        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i] = menuWindow.createButton("", null,
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/+.png")), null, null);
        }
        playerButtons[0].addActionListener(this);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (JButton button : playerButtons) {
            centerPanel.add(Box.createHorizontalGlue());
            centerPanel.add(button);
            centerPanel.add(Box.createHorizontalGlue());
        }
        playerSelectorPanel.add(BorderLayout.CENTER, centerPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        String[] buttonLabels = {"Back", "Next"};
        navigationButtons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            navigationButtons[i] = menuWindow.createButton(buttonLabels[i], this,
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/button.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonHover.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonPressed.png")));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
            buttonPanel.add(navigationButtons[i]);
        }
        navigationButtons[1].setVisible(false);
        buttonPanel.add(Box.createVerticalGlue());
        playerSelectorPanel.add(BorderLayout.SOUTH, buttonPanel);

        JLabel background = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/main/assets/images/gui/backgrounds/background.png")).getImage().getScaledInstance(menuWindow.getFrameSize(),menuWindow.getFrameSize(), Image.SCALE_SMOOTH)));
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(playerSelectorPanel);
    }

    private void playerButtonClick(int index) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/assets/images/gui/playerselector/astronaut" + index + ".gif"));
        playerButtons[index].setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        playerButtons[index].setIcon(icon);
        switch (index) {
            case 0:
                playerNumber = 1;
                playerButtons[index].removeActionListener(this);
                playerButtons[index + 1].addActionListener(this);
                break;
            case 1:
                playerNumber = 2;
                playerButtons[index].removeActionListener(this);
                playerButtons[index + 1].addActionListener(this);
                navigationButtons[1].setVisible(true);
                break;
            case 2:
                playerNumber = 3;
                playerButtons[index].removeActionListener(this);
                break;
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    private void nextButtonClick() {
        menuWindow.initLevelSelector();
        menuWindow.changePanel("RoundSelector");
    }

    private void backButtonClick() {
        resetButtons();
        menuWindow.changePanel("MainMenu");
    }

    private void resetButtons() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/+.png"));
        for (JButton playerButton : playerButtons) {
            playerButton.removeActionListener(this);
            playerButton.setIcon(icon);
            playerButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        playerButtons[0].addActionListener(this);
        navigationButtons[1].setVisible(false);
        playerNumber = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == navigationButtons[1]) {
            nextButtonClick();
        } else if (e.getSource() == navigationButtons[0]) {
            backButtonClick();
        } else if (e.getSource() == playerButtons[0]) {
            playerButtonClick(0);
        } else if (e.getSource() == playerButtons[1]) {
            playerButtonClick(1);
        } else if (e.getSource() == playerButtons[2]) {
            playerButtonClick(2);
        }
    }
}