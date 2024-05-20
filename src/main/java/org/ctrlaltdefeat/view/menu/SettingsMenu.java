package org.ctrlaltdefeat.view.menu;

import org.ctrlaltdefeat.controllers.configuration.ConfigurationManager;
import org.ctrlaltdefeat.controllers.configuration.ControlsProperties;
import org.ctrlaltdefeat.controllers.movement.PlayerControls;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SettingsMenu extends JPanel implements ActionListener {
    private final MenuWindow menuWindow;
    private final JButton[][] controlButtons;
    private final JButton[] navigationButtons;
    private final ConfigurationManager configManager;
    private int activePlayer = -1;
    private int activeControl = -1;
    private int margin = 7;

    public SettingsMenu(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        this.configManager = new ConfigurationManager("controls.properties");
        this.controlButtons = new JButton[3][5];
        this.navigationButtons = new JButton[2];
        this.setPreferredSize(new Dimension(menuWindow.getFrameSize(), menuWindow.getFrameSize()));

        setupLayout();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (activePlayer != -1 && activeControl != -1) {
                    updateControl(activePlayer, activeControl, e.getKeyCode());
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/images/gui/backgrounds/background.png")).getImage().getScaledInstance(menuWindow.getFrameSize(),menuWindow.getFrameSize(), Image.SCALE_SMOOTH)));
        background.setLayout(new BorderLayout());
        background.setPreferredSize(new Dimension(menuWindow.getFrameSize(), menuWindow.getFrameSize()));
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setOpaque(false);

        JLabel titleText = createTitleLabel("Settings");
        settingsPanel.add(titleText);

        settingsPanel.add(Box.createVerticalStrut(margin * 5));

        JPanel playersPanel = createPlayersPanel();
        settingsPanel.add(playersPanel);
        settingsPanel.add(Box.createVerticalStrut(margin * 5));

        JPanel buttonPanel = createButtonPanel();
        settingsPanel.add(buttonPanel);
        background.add(settingsPanel, BorderLayout.CENTER);
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Press Start 2P", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(margin * 7, margin * 2, margin * 2, margin * 2));
        return label;
    }

    private JPanel createPlayersPanel() {
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridBagLayout());
        playersPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(margin * 3, margin * 3, margin * 3, margin * 3);

        for (int i = 0; i < controlButtons.length; i++) {
            JPanel playerPanel = createPlayerPanel(i);
            playersPanel.add(playerPanel, gbc);
        }

        return playersPanel;
    }

    private JPanel createPlayerPanel(int playerIndex) {
        JPanel playerPanel = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Player " + (playerIndex + 1));
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Press Start 2P", Font.BOLD, 13));
        playerPanel.setBorder(border);
        playerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(margin, margin, margin, margin);

        PlayerControls controls = ControlsProperties.getPlayerControls(playerIndex + 1);
        String[] controlNames = {"Up", "Down", "Left", "Right", "Bomb"};
        int[] keyCodes = {controls.getUp(), controls.getDown(), controls.getLeft(), controls.getRight(), controls.getBomb()};

        for (int j = 0; j < controlNames.length; j++) {
            JLabel label = new JLabel(controlNames[j]);
            controlButtons[playerIndex][j] = new JButton(KeyEvent.getKeyText(keyCodes[j]));
            setupControlButton(controlButtons[playerIndex][j], playerIndex, j);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Press Start 2P", Font.BOLD, 10));

            gbc.gridx = j;
            gbc.gridy = 0;
            playerPanel.add(label, gbc);

            gbc.gridy = 1;
            playerPanel.add(controlButtons[playerIndex][j], gbc);
        }

        return playerPanel;
    }

    private void setupControlButton(JButton button, int playerIndex, int controlIndex) {
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(95, 45));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Press Start 2P", Font.BOLD, 10));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.addActionListener(e -> setActiveControl(playerIndex, controlIndex));
        button.setIcon(new ImageIcon(getClass().getResource("/images/gui/buttons/button.png")));
        button.setRolloverIcon(new ImageIcon(getClass().getResource("/images/gui/buttons/buttonHover.png")));
        button.setPressedIcon(new ImageIcon(getClass().getResource("/images/gui/buttons/buttonPressed.png")));

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (activePlayer != -1 && activeControl != -1) {
                    updateControl(playerIndex, controlIndex, e.getKeyCode());
                }
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout());

        navigationButtons[0] = menuWindow.createButton("Back", this,
                new ImageIcon(getClass().getResource("/images/gui/buttons/button.png")),
                new ImageIcon(getClass().getResource("/images/gui/buttons/buttonHover.png")),
                new ImageIcon(getClass().getResource("/images/gui/buttons/buttonPressed.png")));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(navigationButtons[0]);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(margin * 2, margin, margin, margin));
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        return buttonPanel;
    }

    private void setActiveControl(int player, int control) {
        if (activePlayer != -1 && activeControl != -1) {
            controlButtons[activePlayer][activeControl].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }
        activePlayer = player;
        activeControl = control;
        controlButtons[activePlayer][activeControl].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    }

    private void updateControl(int player, int control, int keyCode) {
        controlButtons[player][control].setText(KeyEvent.getKeyText(keyCode));
        controlButtons[player][control].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        String propertyPrefix = "player" + (player + 1) + "_";
        String[] controlNames = {"up", "down", "left", "right", "bomb"};
        configManager.changeProperty(propertyPrefix + controlNames[control], String.valueOf(keyCode));

        activePlayer = -1;
        activeControl = -1;

        requestFocusInWindow();
    }

    private String getActionKeyForButton(JButton button) {
        for (int i = 0; i < controlButtons.length; i++) {
            for (int j = 0; j < controlButtons[i].length; j++) {
                if (controlButtons[i][j].equals(button)) {
                    String[] controlNames = {"up", "down", "left", "right", "bomb"};
                    return "player" + (i + 1) + "_" + controlNames[j];
                }
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == navigationButtons[0]) {
            backButtonClick();
        }
    }

    private void backButtonClick() {
        menuWindow.changePanel("MainMenu");
        System.out.println("Back button clicked");
    }
}
