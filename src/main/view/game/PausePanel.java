package main.view.game;

import main.view.menu.MenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PausePanel extends JPanel implements ActionListener {
    private final GameWindow gameWindow;
    private final JButton[] buttons;
    private JPanel buttonPanel;

    public PausePanel(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        this.setOpaque(false);

        int margin = 15;

        JPanel pausePanel = new JPanel();
        pausePanel.setLayout(new BorderLayout());
        pausePanel.setBackground(new Color(0, 0, 0, 122));

        JLabel titleText = new JLabel("Pause menu", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin*5, margin));
        pausePanel.add(BorderLayout.NORTH, titleText);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        String[] buttonLabels = {"Main menu", "Settings", "Exit"};
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createButton(buttonLabels[i], this,
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/button.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonHover.png")),
                    new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonPressed.png")));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
            buttonPanel.add(buttons[i]);
        }
        buttonPanel.add(Box.createVerticalGlue());
        pausePanel.add(buttonPanel);

        this.setLayout(new BorderLayout());
        this.add(pausePanel);
    }

    private void backToMainMenu() {
        gameWindow.dispose();
        SwingUtilities.invokeLater(() -> new MenuWindow());
    }

    private void settingsButtonClick() {
    }

    private void exitButtonClick() {
        System.exit(0);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            backToMainMenu();
        } else if (e.getSource() == buttons[1]) {
            settingsButtonClick();
        } else if (e.getSource() == buttons[2]) {
            exitButtonClick();
        }
    }
}
