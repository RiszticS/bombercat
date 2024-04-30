package org.ctrlaltdefeat.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    private final int frameSize = 720;
    private final JPanel cards;
    private final MainMenu mainMenu;
    private PlayerSelector playerSelector;
    private LevelSelector levelSelector;

    public MenuWindow() {
        mainMenu = new MainMenu(this);
        playerSelector = new PlayerSelector(this);
        cards = new JPanel(new CardLayout());
        cards.add(mainMenu, "MainMenu");
        cards.add(playerSelector, "PlayerSelector");
        this.add(cards);
        this.setTitle("Bombercat");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(frameSize, frameSize));
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void changePanel(String panelName) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, panelName);
    }

    public void initLevelSelector() {
        levelSelector = new LevelSelector(this);
        cards.add(levelSelector, "LevelSelector");
    }

    public PlayerSelector getPlayerSelector(){
        return playerSelector;
    }

    public JButton createButton(String text, ActionListener actionListener, ImageIcon icon, ImageIcon iconHover, ImageIcon iconPressed) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setIcon(icon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.setFont(new Font("Press Start 2P", Font.BOLD, 15));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        if (actionListener != null) button.addActionListener(actionListener);
        if (iconHover != null) button.setRolloverIcon(iconHover);
        if (iconPressed != null) button.setPressedIcon(iconPressed);
        return button;
    }
}