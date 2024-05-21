package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.model.GameModel;
import org.ctrlaltdefeat.view.menu.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {

    private final GamePanel gamePanel;
    private final GameModel gameModel;
    private final PausePanel pausePanel;
    private final GuiPanel guiPanel;
    private final JLayeredPane layeredPane;
    private boolean isPaused;

    public GameWindow(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Bomberman");
        isPaused = false;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gamePanel = new GamePanel(gameModel, this);
        guiPanel = new GuiPanel(gameModel);
        pausePanel = new PausePanel(this);
        pausePanel.setVisible(false);

        centerPanel.add(gamePanel, gbc);

        mainPanel.add(guiPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(900, 900));

        // Háttérkép hozzáadása
        JLabel background = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/images/gui/backgrounds/background.png")).getImage().getScaledInstance(900, 900, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 900);
        layeredPane.add(background, Integer.valueOf(Integer.MIN_VALUE));

        mainPanel.setBounds(0, 0, 900, 900);
        pausePanel.setBounds(0, 0, 900, 900);

        layeredPane.add(mainPanel, Integer.valueOf(0));
        layeredPane.add(pausePanel, Integer.valueOf(2));

        this.getContentPane().add(layeredPane);
        this.pack();
        this.setSize(new Dimension(900, 900));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        try {
            this.setIconImage(new ImageIcon(ImageIO.read(GameWindow.class.getResourceAsStream("/images/gui/icons/icon.png"))).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setupKeyBindings();
    }

    public void addPanel(Component component, Integer layer) {
        component.setBounds(0, 0, 900, 900);
        layeredPane.add(component, layer);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void setupKeyBindings() {
        KeyStroke escKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action escAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKeyStroke, "PauseGame");
        getRootPane().getActionMap().put("PauseGame", escAction);
    }

    private void togglePause() {
        isPaused = !isPaused;
        pausePanel.setVisible(isPaused);
        if (isPaused) {
            layeredPane.moveToFront(pausePanel);
        } else {
            layeredPane.moveToBack(pausePanel);
            gamePanel.requestFocusInWindow();
        }
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GuiPanel getGuiPanel(){
        return guiPanel;
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