package org.ctrlaltdefeat.view.menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RoundSelector extends JPanel implements ActionListener {
    private final MenuWindow menuWindow;
    private final JButton[] roundButtons;
    private JButton navigationButton;
    private int roundNumber = 0;

    public RoundSelector(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        int margin = 15;

        JPanel roundSelectorPanel = new JPanel();
        roundSelectorPanel.setLayout(new BorderLayout());
        roundSelectorPanel.setOpaque(false);

        JLabel titleText = new JLabel("Round selector", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin, margin));
        roundSelectorPanel.add(BorderLayout.NORTH, titleText);

        roundButtons = new JButton[3];
        for (int i = 0; i < roundButtons.length; i++) {
            try {
                roundButtons[i] = menuWindow.createButton("" + (3 + i) + "", this,
                        new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/buttonbig.png"))),
                        new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/buttonbigHover.png"))),
                        new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/buttonbigPressed.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        roundButtons[0].addActionListener(this);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (JButton button : roundButtons) {
            centerPanel.add(Box.createHorizontalGlue());
            centerPanel.add(button);
            centerPanel.add(Box.createHorizontalGlue());
        }
        roundSelectorPanel.add(BorderLayout.CENTER, centerPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        navigationButton = null;
        try {
            navigationButton = menuWindow.createButton("Back", this,
                    new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/button.png"))),
                    new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/buttonHover.png"))),
                    new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/buttons/buttonPressed.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(navigationButton);

        buttonPanel.add(Box.createVerticalGlue());
        roundSelectorPanel.add(BorderLayout.SOUTH, buttonPanel);

        JLabel background = null;
        try {
            background = new JLabel(new ImageIcon(new ImageIcon(ImageIO.read(RoundSelector.class.getResourceAsStream("/images/gui/backgrounds/background.png"))).getImage().getScaledInstance(menuWindow.getFrameSize(), menuWindow.getFrameSize(), Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(roundSelectorPanel);
    }

    private void roundButtonClick(int index) {
        roundNumber =  Integer.parseInt(roundButtons[index].getText());
        nextButtonClick();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    private void nextButtonClick() {
        menuWindow.initLevelSelector();
        menuWindow.changePanel("LevelSelector");
    }

    private void backButtonClick() {
        menuWindow.changePanel("PlayerSelector");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == navigationButton) {
            backButtonClick();
        }else if (e.getSource() == roundButtons[0]) {
            roundButtonClick(0);
        } else if (e.getSource() == roundButtons[1]) {
            roundButtonClick(1);
        } else if (e.getSource() == roundButtons[2]) {
            roundButtonClick(2);
        }
    }
}
