package view;

import controller.GameLoop;
import model.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame implements ActionListener {

    private JLabel titleText;
    private final int buttonWidth = 100;
    private final int buttonHeight = 30;
    private JButton newGameButton;
    private JButton mapEditorButton;
    private JButton exitButton;
    private JPanel buttonPanel;

    private MenuPanel menuPanel;
    public MenuWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(720, 720));
        this.setTitle("Bomberman");
        //this.getContentPane().add(menuPanel);

        this.titleText = new JLabel("Multiplayer Bomberman");
        Font fnt = new Font("Calibri",Font.BOLD,30);
        titleText.setFont(fnt);
        titleText.setForeground(Color.WHITE);
        //titleText.setHorizontalAlignment(SwingConstants.CENTER);
        this.titleText.setAlignmentX(CENTER_ALIGNMENT);
        this.titleText.setAlignmentY(CENTER_ALIGNMENT);


        this.newGameButton = new JButton();
        this.mapEditorButton = new JButton();
        this.exitButton = new JButton();

        Dimension buttonDimension = new Dimension(buttonWidth, buttonHeight);

        this.newGameButton = new JButton("New Game");
        this.newGameButton.setPreferredSize(buttonDimension);
        this.newGameButton.setMinimumSize(buttonDimension);
        this.newGameButton.setAlignmentX(CENTER_ALIGNMENT);
        this.newGameButton.setAlignmentY(CENTER_ALIGNMENT);
        this.newGameButton.addActionListener(this);

        //newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.newGameButton.setFocusable(false);

        this.mapEditorButton = new JButton("Map Editor");
        this.mapEditorButton.setPreferredSize(buttonDimension);
        this.mapEditorButton.setAlignmentX(CENTER_ALIGNMENT);
        this.mapEditorButton.addActionListener(this);
        //mapEditorButton.setHorizontalAlignment(SwingConstants.CENTER);

        this.mapEditorButton.setFocusable(false);

        this.exitButton = new JButton("Exit");
        this.exitButton.setPreferredSize(buttonDimension);
        this.exitButton.setMinimumSize(buttonDimension);
        this.exitButton.setAlignmentX(CENTER_ALIGNMENT);
        this.exitButton.setAlignmentY(CENTER_ALIGNMENT);
        this.exitButton.addActionListener(this);
        //this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.exitButton.setFocusable(false);


        this.buttonPanel = new JPanel();
        this.buttonPanel.setPreferredSize(new Dimension(720, 720));
        this.buttonPanel.setMinimumSize(new Dimension(720, 720));
        this.buttonPanel.setBackground(Color.BLUE);
        //buttonPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        buttonPanel.setBackground(Color.black);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //buttonPanel.setBorder(new EmptyBorder(new Insets(200, 285, 120, 285)));
        buttonPanel.setVisible(true);

        this.buttonPanel.add(titleText);
        this.buttonPanel.add(newGameButton);
        this.buttonPanel.add(mapEditorButton);
        this.buttonPanel.add(exitButton);
        this.buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.add(buttonPanel);
        this.pack();
        this.setVisible(true);
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
