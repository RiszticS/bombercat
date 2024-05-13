package main.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelector extends JPanel implements ActionListener {

    public final MenuWindow menuWindow;
    private final JButton backButton;
    private final LevelGamePanel levelGamePanel;

    public LevelSelector(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        int margin = 15;

        levelGamePanel = new LevelGamePanel(this);
        levelGamePanel.setLayout(new BorderLayout());
        levelGamePanel.setOpaque(false);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
            levelGamePanel.requestFocus();
        });

        JLabel titleText = new JLabel("Level selector", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin, margin));
        levelGamePanel.add(BorderLayout.NORTH, titleText);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        backButton = menuWindow.createButton("Back", this, new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/button.png")), new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonHover.png")), new ImageIcon(getClass().getResource("/main/assets/images/gui/buttons/buttonPressed.png")));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalGlue());
        levelGamePanel.add(BorderLayout.SOUTH, buttonPanel);

        JLabel background = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/main/assets/images/gui/backgrounds/background.png")).getImage().getScaledInstance(menuWindow.getFrameSize(),menuWindow.getFrameSize(), Image.SCALE_SMOOTH)));
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(levelGamePanel);
    }

    private void backButtonClick() {
        menuWindow.changePanel("RoundSelector");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            backButtonClick();
        }
    }
}