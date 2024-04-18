package views.menu;

import controllers.game.GameLoop;
import models.Direction;
import models.GameModel;
import models.Position;
import views.game.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelGamePanel extends JPanel {

    private final BufferedImage rocketImage;
    private final Position rocketPosition = new Position(350, 200);
    private static final int ROCKET_SIZE = 50;
    private static final int MOVE_DISTANCE = 5;

    private final BufferedImage[] levelsImages;
    private final Position[] levelsPositions;
    private static final int LEVELS_SIZE = 200;

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

        int[] levelPositionX = {10, 490, 580};
        int[] levelPositionY = {200, 480, 20};
        rocketImage = loadImage("src/assets/images/gui/levelselector/rocket.png");
        levelsImages = new BufferedImage[3];
        levelsPositions = new Position[3];
        for (int i = 0; i < levelsImages.length; i++) {
            levelsImages[i] = loadImage("src/assets/images/gui/levelselector/level" + (i + 1) + ".png");
            levelsPositions[i] = new Position(levelPositionX[i], levelPositionY[i]);
        }
        movement();
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

    private void startGame(int level) {
        timer.stop();
        GameModel gm = new GameModel(level, levelSelector.menuWindow.playerSelector.getPlayerNumber());
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
        for (int i = 0; i < levelsPositions.length; i++) {
            Ellipse2D.Double levelCircle = new Ellipse2D.Double(
                    levelsPositions[i].getX(),
                    levelsPositions[i].getY(),
                    LEVELS_SIZE,
                    LEVELS_SIZE
            );
            if (levelCircle.intersects(rocket) && !gameStarted) {
                gameStarted = true;
                startGame(i + 1);
                break;
            }
        }
    }

    private BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
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
        for (int i = 0; i < levelsPositions.length; i++) {
            g.drawImage(levelsImages[i], levelsPositions[i].getX(), levelsPositions[i].getY(), LEVELS_SIZE, LEVELS_SIZE, this);
        }
    }
}