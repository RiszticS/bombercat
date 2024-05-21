package org.ctrlaltdefeat.view.menu;
import org.ctrlaltdefeat.controllers.ResourceWalker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class MapEditor extends JPanel implements ActionListener {
    private final MenuWindow menuWindow;
    private final JButton[][] boardButtons;
    private final JButton[] buttons;
    private JButton[] editButtons;
    private Icon currentTileIcon;
    private File[] tiles;
    private final String folderPath = "/images/tiles/";
    private String selectedTheme = "spacestation/";
    private JComboBox<String> themeDropdown;
    private JPanel rightSidePanel;
    private JLabel selectedTile;
    private JPanel comboBoxPanel;
    private int loadIndex;
    private int createdLevelAssetNumber;

    public MapEditor(MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        themeDropdown = new JComboBox<>();
        themeDropdown.setFont(new Font("Press Start 2P", Font.BOLD, 12));
        themeDropdown.setBackground(new Color(34, 32, 52));
        themeDropdown.setForeground(Color.white);
        themeDropdown.setPreferredSize(new Dimension(200, 25));

        comboBoxPanel = new JPanel();
        comboBoxPanel.setPreferredSize(new Dimension(200, 100));
        comboBoxPanel.setOpaque(false);
        comboBoxPanel.add(themeDropdown);

        int margin = 15;
        loadIndex = -1;

        JPanel mapEditorPanel = new JPanel();
        mapEditorPanel.setLayout(new BorderLayout());
        mapEditorPanel.setOpaque(false);
        JLabel titleText = new JLabel("Map editor", SwingConstants.CENTER);
        titleText.setFont(new Font("Press Start 2P", Font.BOLD, 30));
        titleText.setForeground(Color.WHITE);
        titleText.setAlignmentY(CENTER_ALIGNMENT);
        titleText.setBorder(BorderFactory.createEmptyBorder(margin * 5, margin, margin, margin));
        mapEditorPanel.add(BorderLayout.NORTH, titleText);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        boardButtons = new JButton[15][15];
        for (int i = 0; i < boardButtons.length; i++) {
            for (int j = 0; j < boardButtons[i].length; j++) {
                try {
                    boardButtons[i][j] = menuWindow.createButton("", this, new ImageIcon(new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/+.png"))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)), null, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                centerPanel.add(boardButtons[i][j], gbc);
            }
            gbc.gridy++;
        }
        mapEditorPanel.add(BorderLayout.CENTER, centerPanel);

        selectedTile = new JLabel();

        rightSidePanel = new JPanel();
        loadTileImages(selectedTheme);
        rightSidePanel.add(comboBoxPanel);
        rightSidePanel.setLayout(new GridLayout(0, 1));
        rightSidePanel.setOpaque(false);
        for (JButton button : editButtons) {
            rightSidePanel.add(button);
        }

        mapEditorPanel.add(BorderLayout.EAST, rightSidePanel);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setOpaque(false);
        String[] buttonLabels = {"Back", "Set All", "Clear", "Load", "Save"};
        buttons = new JButton[buttonLabels.length];
        try {
            buttons[0] = menuWindow.createButton(buttonLabels[0], this,
                    new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/button.png"))),
                    new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/buttonHover.png"))),
                    new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/buttonPressed.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigationPanel.add(Box.createRigidArea(new Dimension(0, margin)));
        navigationPanel.add(buttons[0]);

        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
        leftSidePanel.setOpaque(false);

        JPanel currentTilePanel = new JPanel(new GridLayout(1, 1));
        JLabel currentTileText = new JLabel("Current:");
        currentTileText.setFont(new Font("Press Start 2P", Font.BOLD, 12));
        currentTileText.setForeground(Color.white);
        currentTilePanel.add(currentTileText);
        currentTilePanel.setOpaque(false);
        currentTilePanel.add(selectedTile);
        leftSidePanel.add(currentTilePanel);

        for (int i = 1; i < buttonLabels.length; i++) {
            try {
                buttons[i] = menuWindow.createButton(buttonLabels[i], this,
                        new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/buttonSmall.png"))),
                        new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/buttonSmallHover.png"))),
                        new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/buttonSmallPressed.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            leftSidePanel.add(Box.createRigidArea(new Dimension(200, margin * 2)));
            leftSidePanel.add(buttons[i]);
        }
        buttons[4].setVisible(false);

        mapEditorPanel.add(BorderLayout.WEST, leftSidePanel);

        themeDropdown.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selectedOption = (String) combo.getSelectedItem();
            selectedTheme = selectedOption + "/";
            loadTileImages(selectedTheme);
        });

        mapEditorPanel.add(BorderLayout.SOUTH, navigationPanel);

        JLabel background = null;
        try {
            background = new JLabel(new ImageIcon(new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/backgrounds/background.png"))).getImage().getScaledInstance(menuWindow.getFrameSize(), menuWindow.getFrameSize(), Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(new BorderLayout());
        this.add(background);
        this.setOpaque(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.add(Box.createVerticalGlue());
        background.add(mapEditorPanel);

        createdLevelAssetNumber = 0;
        try {
            createdLevelAssetNumber = ResourceWalker.walk("/levels/createdLevels").size();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        buttons[3].setVisible(createdLevelAssetNumber > 0);
    }

    private void backButtonClick() {
        menuWindow.changePanel("MainMenu");
    }

    private void boardButtonClick(JButton mapButton) {
        mapButton.setIcon(currentTileIcon);
        mapButton.setPreferredSize(new Dimension(currentTileIcon.getIconWidth(), currentTileIcon.getIconHeight()));
        buttons[4].setVisible(false);
        validateMap(boardButtons);
    }

    public void validateMap(JButton[][] boardButtons) {
        buttons[4].setVisible(false);
        int rows = boardButtons.length;
        int cols = boardButtons[0].length;

        boolean topWall = true;
        boolean bottomWall = true;
        boolean leftWall = true;
        boolean rightWall = true;
        int playerCount = 0;

        for (int j = 0; j < cols; j++) {
            if (!boardButtons[0][j].getIcon().toString().contains("wall")) {
                topWall = false;
                break;
            }
        }
        for (int j = 0; j < cols; j++) {
            if (!boardButtons[rows - 1][j].getIcon().toString().contains("wall")) {
                bottomWall = false;
                break;
            }
        }
        for (int i = 1; i < rows - 1; i++) {
            if (!boardButtons[i][0].getIcon().toString().contains("wall")) {
                leftWall = false;
                break;
            }
        }
        for (int i = 1; i < rows - 1; i++) {
            if (!boardButtons[i][cols - 1].getIcon().toString().contains("wall")) {
                rightWall = false;
                break;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boardButtons[i][j].getIcon().toString().contains("player")) {
                    playerCount++;
                }
            }
        }
        if (topWall && bottomWall && leftWall && rightWall && playerCount == 3) buttons[4].setVisible(true);
    }

    private void setCurrentTileIcon(Icon icon) {
        currentTileIcon = icon;
        selectedTile.setIcon(currentTileIcon);
    }

    private void setAllButtonsCurrentIcon() {
        for (JButton[] button : boardButtons) {
            for (JButton mapButton : button) {
                mapButton.setIcon(currentTileIcon);
            }
        }
        validateMap(boardButtons);
    }

    private void clearBoardButtons() {
        for (JButton[] button : boardButtons) {
            for (JButton mapButton : button) {
                try {
                    mapButton.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(MapEditor.class.getResourceAsStream("/images/gui/buttons/+.png"))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        validateMap(boardButtons);
    }

    private void saveLevel() {
        File folder = new File(System.getenv("game_path") + "/levels/createdLevels");
        int index = Objects.requireNonNull(folder.listFiles()).length;
        try {
            FileWriter writer = new FileWriter(System.getenv("game_path") + "/levels/createdLevels/" + "level" + index + ".txt");
            for (int i = 0; i < boardButtons.length; i++) {
                for (int j = 0; j < boardButtons[i].length; j++) {
                    Icon icon = boardButtons[i][j].getIcon();
                    String iconName = findIconName(icon);
                    writer.write(iconName + ";");
                }
                if (i != boardButtons.length - 1) writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving level: " + e.getMessage());
        }
        buttons[3].setVisible(createdLevelAssetNumber > 0);
    }

    private void loadLevel() {
        if (createdLevelAssetNumber > 0) {
            if (loadIndex < createdLevelAssetNumber - 1) loadIndex++;
            else loadIndex = 0;
            try {
                File file = new File(System.getenv("game_path") + "/levels/createdLevels/level" + loadIndex + ".txt");
                Scanner scanner = new Scanner(file);
                for (int i = 0; i < boardButtons.length; i++) {
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    String line = scanner.nextLine();
                    String[] iconNames = line.split(";");
                    for (int j = 0; j < boardButtons[i].length; j++) {
                        String iconName = iconNames[j];
                        for (JButton button : editButtons) {
                            if (Objects.equals(findIconName(button.getIcon()), iconName)) {
                                boardButtons[i][j].setIcon(button.getIcon());
                                break;
                            }
                        }
                    }
                }
                scanner.close();
            } catch (IOException e) {
                System.err.println("Error loading level: " + e.getMessage());
            }
            validateMap(boardButtons);
        }
    }

    private String findIconName(Icon icon) {
        for (int i = 0; i < editButtons.length; i++) {
            if (editButtons[i].getIcon().equals(icon)) {
                return tiles[i].getName().replace(".png", "");
            }
        }
        return "";
    }

    private void setThemeDropdown() {
        File folder = new File(System.getenv("game_path") + folderPath);
        File[] themes = folder.listFiles();
        themeDropdown.removeAllItems();

        if (themes != null) {
            for (File theme : themes) {
                if (theme.isDirectory()) {
                    themeDropdown.addItem(theme.getName());
                }
            }
        }
        themeDropdown.setSelectedItem(selectedTheme.substring(0, selectedTheme.length() - 1));
    }


    private void loadTileImages(String selectedTheme) {
        File folder = new File(System.getenv("game_path") + folderPath + selectedTheme);
        tiles = folder.listFiles();

        if (tiles != null) {
            setCurrentTileIcon(new ImageIcon(String.valueOf(tiles[0])));
            setThemeDropdown();
            editButtons = new JButton[tiles.length];
            rightSidePanel.removeAll();
            rightSidePanel.add(comboBoxPanel);
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i].isFile()) {
                    ImageIcon imageIcon = new ImageIcon(tiles[i].getPath());
                    JButton button = menuWindow.createButton("", this, imageIcon, null, null);
                    editButtons[i] = button;
                    rightSidePanel.add(button);
                }
            }
            rightSidePanel.revalidate();
            rightSidePanel.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            backButtonClick();
        } else if (e.getSource() == buttons[4]) {
            saveLevel();
        } else if (e.getSource() == buttons[1]) {
            setAllButtonsCurrentIcon();
        } else if (e.getSource() == buttons[2]) {
            clearBoardButtons();
        } else if (e.getSource() == buttons[3]) {
            loadLevel();
        }
        for (JButton[] button : boardButtons) {
            for (JButton mapButton : button) {
                if (e.getSource() == mapButton) {
                    boardButtonClick(mapButton);
                }
            }
        }
        for (JButton button : editButtons) {
            if (e.getSource() == button) {
                setCurrentTileIcon(button.getIcon());
            }
        }
    }
}
