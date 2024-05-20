package org.ctrlaltdefeat.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {

    private final int frameSize = 900;
    private final JPanel cards;
    private final MainMenu mainMenu;
    private final PlayerSelector playerSelector;
    private final RoundSelector roundSelector;
    private LevelSelector levelSelector;
    private final MapEditor mapEditor;
    private SettingsMenu settingsMenu;

    public MenuWindow() {
        mainMenu = new MainMenu(this);
        playerSelector = new PlayerSelector(this);
        settingsMenu = new SettingsMenu(this);
        roundSelector = new RoundSelector(this);
        mapEditor = new MapEditor(this);
        cards = new JPanel(new CardLayout());
        cards.add(mainMenu, "MainMenu");
        cards.add(playerSelector, "PlayerSelector");
        cards.add(roundSelector, "RoundSelector");
        cards.add(mapEditor, "MapEditor");
        cards.add(settingsMenu, "SettingsMenu");
        this.add(cards);
        this.setTitle("Bombercat");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(frameSize, frameSize));
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/gui/icon.png")).getImage());
    }

    public void changePanel(String panelName) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, panelName);
    }

    public void initLevelSelector() {
        levelSelector = new LevelSelector(this);
        cards.add(levelSelector, "LevelSelector");
    }

    public PlayerSelector getPlayerSelector() {
        return playerSelector;
    }

    public RoundSelector getRoundSelector() {
        return roundSelector;
    }

    public int getFrameSize() {
        return frameSize;
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