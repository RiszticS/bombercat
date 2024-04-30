package views.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JPanel implements ActionListener {
    private MenuWindow menuWindow;
    private JButton[] buttons;
    private JPanel buttonPanel;

    public MainMenu(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        int margin = 15;

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        String[] buttonLabels = {"New Game", "Map editor", "Settings", "Exit"};
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = menuWindow.createButton(buttonLabels[i], this, new ImageIcon(getClass().getResource("/assets/images/gui/buttons/button.png")), new ImageIcon(getClass().getResource("/assets/images/gui/buttons/buttonHover.png")), new ImageIcon(getClass().getResource("/assets/images/gui/buttons/buttonPressed.png")));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
            buttonPanel.add(buttons[i]);
        }
        buttonPanel.add(Box.createVerticalGlue());

        ImageIcon backgroundImage=new ImageIcon(getClass().getResource("/assets/images/gui/backgrounds/mainMenuBackground.gif"));
        backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(menuWindow.getFrameSize(), menuWindow.getFrameSize(),Image.SCALE_DEFAULT));
        JLabel background = new JLabel(backgroundImage);
        background.setSize(menuWindow.getFrameSize(), menuWindow.getFrameSize());
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(buttonPanel);
    }

    private void newGameButtonClick() {
        menuWindow.changePanel("PlayerSelector");
    }

    private void mapEditorButtonClick() {
        menuWindow.changePanel("MapEditor");
    }

    private void settingsButtonClick() {
    }

    private void exitButtonClick() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            newGameButtonClick();
        } else if (e.getSource() == buttons[1]) {
            mapEditorButtonClick();
        } else if (e.getSource() == buttons[2]) {
            settingsButtonClick();
        } else if (e.getSource() == buttons[3]) {
            exitButtonClick();
        }
    }
}