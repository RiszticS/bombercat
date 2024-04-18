package main.views.menu;

import main.controllers.game.GameLoop;
import main.models.GameModel;
import main.views.game.GameWindow;

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
            playerButtons[i]=menuWindow.createButton("",null,new ImageIcon(getClass().getResource("/main/assets/images/gui/+.png")),null,null);
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
            navigationButtons[i] =  menuWindow.createButton(buttonLabels[i],this, new ImageIcon(getClass().getResource("/main/assets/images/gui/button.png")),new ImageIcon(getClass().getResource("/main/assets/images/gui/buttonHover.png")),new ImageIcon(getClass().getResource("/main/assets/images/gui/buttonPressed.png")));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
            buttonPanel.add(navigationButtons[i]);
        }
        navigationButtons[1].setVisible(false);
        buttonPanel.add(Box.createVerticalGlue());
        playerSelectorPanel.add(BorderLayout.SOUTH, buttonPanel);

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/main/assets/images/gui/background.png")));
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(playerSelectorPanel);
    }

    private void playerButtonClick(int index) {
        playerNumber++;
        ImageIcon icon =new ImageIcon(getClass().getResource("/main/assets/images/gui/astronaut" + index + ".gif"));
        playerButtons[index].setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));;
        playerButtons[index].setIcon(icon);
        switch (index) {
            case 0:
                playerButtons[index].removeActionListener(this);
                playerButtons[index + 1].addActionListener(this);
                navigationButtons[1].setVisible(true);
                break;
            case 1:
                playerButtons[index].removeActionListener(this);
                playerButtons[index + 1].addActionListener(this);
                break;
            case 2:
                playerButtons[index].removeActionListener(this);
                break;
        }
    }

    private void nextButtonClick() {
        GameModel gm = new GameModel(1);
        GameWindow gw = new GameWindow(gm);
        GameLoop gc = new GameLoop(gm, gw.getGamePanel());
        gc.start();
        menuWindow.dispose();
    }

    private void backButtonClick() {
        menuWindow.changePanel("MainMenu");
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