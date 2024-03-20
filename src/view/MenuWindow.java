package view;

import controller.GameLoop;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuWindow extends JFrame implements ActionListener {
    private JLabel titleText;
    private final int frameSize = 720;
    private final int margin = 15;
    private JButton newGameButton;
    private JButton mapEditorButton;
    private JButton settingsButton;
    private JButton exitButton;
    private JPanel buttonPanel;
    private final Font fnt;
    private final Font fnt2;
    private ImageIcon icon;

    public MenuWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(frameSize, frameSize));
        this.setTitle("Bomberman");

        this.titleText = new JLabel("Multiplayer Bomberman");
        fnt = new Font("Press Start 2P",Font.BOLD,30);
        titleText.setFont(fnt);
        titleText.setForeground(Color.WHITE);
        this.titleText.setAlignmentX(CENTER_ALIGNMENT);
        this.titleText.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin*5, margin));

        System.out.println(new File("").getAbsolutePath());
        fnt2 = new Font("Press Start 2P",Font.BOLD,15);
        icon = new ImageIcon(getClass().getResource("/assets/images/button.png"));
        newGameButton = createButton("New Game", icon);
        mapEditorButton = createButton("Map editor", icon);
        settingsButton = createButton("Settings", icon);
        exitButton = createButton("Exit", icon);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(mapEditorButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(settingsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());


        this.setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/images/menuHatter.png")));
        this.add(background);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(titleText);
        background.add(buttonPanel);

        this.setSize(frameSize,frameSize);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private JButton createButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setIcon(icon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.setFont(fnt2);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.addActionListener(this);
        button.setFocusable(false);
        return button;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGameButton) {
            this.dispose();
            GameModel gm = new GameModel(2);
            GameWindow gw = new GameWindow(gm);
            GameLoop gc = new GameLoop(gm, gw.getGamePanel());
            gc.start();
        }
    }
}