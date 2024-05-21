package org.ctrlaltdefeat.view.menu;

import org.ctrlaltdefeat.controllers.ResourceWalker;
import org.ctrlaltdefeat.controllers.game.GameLoop;
import org.ctrlaltdefeat.model.GameModel;
import org.ctrlaltdefeat.model.positions.CoordinatePosition;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.view.game.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class LevelGamePanel extends JPanel {

    private final BufferedImage rocketImage;
    private final CoordinatePosition rocketPosition;
    private static final int ROCKET_SIZE = 50;
    private static final int MOVE_DISTANCE = 5;

    private BufferedImage[] mainLevelsImages;
    private CoordinatePosition[] mainLevelsPositions;
    private static final int MAIN_LEVELS_SIZE = 200;

    private BufferedImage[] createdLevelsImages;
    private CoordinatePosition[] createdLevelsPositions;
    private static final int CREATED_LEVELS_SIZE = 50;
    private ArrayList<String> createdLevels;

    private final LevelSelector levelSelector;
    private Timer timer;

    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private Direction currentDirection = Direction.UP;
    private boolean gameStarted = false;

    public LevelGamePanel(LevelSelector levelSelector) {
        this.levelSelector = levelSelector;
        setFocusable(true);
        requestFocusInWindow();
        setOpaque(false);

        rocketImage = loadImage("/images/gui/levelselector/rocket.png");
        rocketPosition = new CoordinatePosition(350, 200);

        loadMainLevels();
        loadCreatedLevels();

        movement();
    }

    public void loadMainLevels() {
        int[] mainLevelPositionX = {10, 490, 650};
        int[] mainLevelPositionY = {200, 600, 20};
        mainLevelsImages = new BufferedImage[3];
        mainLevelsPositions = new CoordinatePosition[3];
        for (int i = 0; i < mainLevelsImages.length; i++) {
            mainLevelsImages[i] = loadImage("/images/gui/levelselector/level" + (i + 1) + ".png");
            mainLevelsPositions[i] = new CoordinatePosition(mainLevelPositionX[i], mainLevelPositionY[i]);
        }
    }

    public void loadCreatedLevels() {
        try {
            createdLevels = ResourceWalker.walk("/levels");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        int length = createdLevels.size() - 1;

        int[] createdLevelPositionX = new int[length];
        int[] createdLevelPositionY = new int[length];
        fillWithRandomNumbers(createdLevelPositionX, createdLevelPositionY);

        createdLevelsImages = new BufferedImage[length];
        createdLevelsPositions = new CoordinatePosition[length];
        for (int i = 0; i < createdLevels.size() - 1; i++) {
            createdLevelsImages[i] = loadImage("/images/gui/levelselector/meteor.png");
            createdLevelsPositions[i] = new CoordinatePosition(createdLevelPositionX[i], createdLevelPositionY[i]);
        }
    }

    public void fillWithRandomNumbers(int[] arrayX, int[] arrayY) {
        Random rand = new Random();
        int minDistance = 50;
        for (int i = 0; i < arrayX.length; i++) {
            boolean valid = false;
            int centerX = 0;
            int centerY = 0;
            while (!valid) {
                centerX = rand.nextInt(levelSelector.menuWindow.getFrameSize() - 200) + 100;
                centerY = rand.nextInt(levelSelector.menuWindow.getFrameSize() - 200) + 100;
                int radius = CREATED_LEVELS_SIZE;

                valid = true;
                for (int j = 0; j < mainLevelsPositions.length; j++) {
                    int mainX = mainLevelsPositions[j].getX();
                    int mainY = mainLevelsPositions[j].getY();
                    int mainRadius = MAIN_LEVELS_SIZE;
                    double distance = Math.sqrt(Math.pow(centerX - mainX, 2) + Math.pow(centerY - mainY, 2));
                    if (distance <= radius + mainRadius + minDistance) {
                        valid = false;
                        break;
                    }
                }
                int distanceSquared = (centerX - rocketPosition.getX()) * (centerX - rocketPosition.getX()) + (centerY - rocketPosition.getY()) * (centerY - rocketPosition.getY());
                if (distanceSquared < 100 * 100) {
                    valid = false;
                }
            }
            arrayX[i] = centerX;
            arrayY[i] = centerY;
        }
    }


    private void movement() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W:
                        upPressed = true;
                        break;
                    case KeyEvent.VK_S:
                        downPressed = true;
                        break;
                    case KeyEvent.VK_A:
                        leftPressed = true;
                        break;
                    case KeyEvent.VK_D:
                        rightPressed = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W:
                        upPressed = false;
                        break;
                    case KeyEvent.VK_S:
                        downPressed = false;
                        break;
                    case KeyEvent.VK_A:
                        leftPressed = false;
                        break;
                    case KeyEvent.VK_D:
                        rightPressed = false;
                        break;
                }
            }
        });

        timer = new Timer(16, e -> {
            if (upPressed) {
                moveRocket(0, -MOVE_DISTANCE);
                currentDirection = Direction.UP;
            }
            if (downPressed) {
                moveRocket(0, MOVE_DISTANCE);
                currentDirection = Direction.DOWN;
            }
            if (leftPressed) {
                moveRocket(-MOVE_DISTANCE, 0);
                currentDirection = Direction.LEFT;
            }
            if (rightPressed) {
                moveRocket(MOVE_DISTANCE, 0);
                currentDirection = Direction.RIGHT;
            }
        });
        timer.start();
    }

    private void moveRocket(int dx, int dy) {
        int newX = rocketPosition.getX() + dx;
        int newY = rocketPosition.getY() + dy;
        if (isValidMove(newX, newY)) {
            rocketPosition.changeX(dx);
            rocketPosition.changeY(dy);
            repaint();
            checkCollisions();
        }
    }

    private void startGame(int level,boolean createdLevel) {
        timer.stop();
        GameModel gm = new GameModel(level, levelSelector.menuWindow.getPlayerSelector().getPlayerNumber(), levelSelector.menuWindow.getRoundSelector().getRoundNumber(),createdLevel);
        GameWindow gw = new GameWindow(gm);
        GameLoop gc = new GameLoop(gm, gw.getGamePanel());
        gc.start();
        levelSelector.menuWindow.dispose();
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && y >= 0 && x + ROCKET_SIZE <= getWidth() && y + ROCKET_SIZE <= getHeight();
    }

    private void checkCollisions() {
        Rectangle rocket = new Rectangle(rocketPosition.getX() + 12, rocketPosition.getY(), ROCKET_SIZE - (ROCKET_SIZE / 2), ROCKET_SIZE);
        for (int i = 0; i < mainLevelsPositions.length; i++) {
            Ellipse2D.Double levelCircle = new Ellipse2D.Double(
                    mainLevelsPositions[i].getX(),
                    mainLevelsPositions[i].getY(),
                    MAIN_LEVELS_SIZE,
                    MAIN_LEVELS_SIZE
            );
            if (levelCircle.intersects(rocket) && !gameStarted) {
                gameStarted = true;
                startGame(i ,false);
                break;
            }
        }
        for (int i = 0; i < createdLevelsPositions.length; i++) {
            Ellipse2D.Double levelCircle = new Ellipse2D.Double(
                    createdLevelsPositions[i].getX(),
                    createdLevelsPositions[i].getY(),
                    CREATED_LEVELS_SIZE,
                    CREATED_LEVELS_SIZE
            );
            if (levelCircle.intersects(rocket) && !gameStarted) {
                gameStarted = true;
                startGame(i,true);
                break;
            }
        }
    }

    private BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(LevelGamePanel.class.getResourceAsStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage rotateImage(BufferedImage image, Direction direction) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        double angle = switch (direction) {
            case Direction.LEFT -> -90;
            case Direction.RIGHT -> 90;
            case Direction.UP -> 0;
            case Direction.DOWN -> 180;
            default -> 0;
        };

        g2d.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage rotatedRocketImage = rotateImage(rocketImage, currentDirection);
        g.drawImage(rotatedRocketImage, rocketPosition.getX(), rocketPosition.getY(), ROCKET_SIZE, ROCKET_SIZE, this);
        for (int i = 0; i < mainLevelsPositions.length; i++) {
            g.drawImage(mainLevelsImages[i], mainLevelsPositions[i].getX(), mainLevelsPositions[i].getY(), MAIN_LEVELS_SIZE, MAIN_LEVELS_SIZE, this);
        }
        for (int i = 0; i < createdLevels.size() - 1; i++) {
            g.drawImage(createdLevelsImages[i], createdLevelsPositions[i].getX(), createdLevelsPositions[i].getY(), CREATED_LEVELS_SIZE, CREATED_LEVELS_SIZE, this);
        }
    }
}