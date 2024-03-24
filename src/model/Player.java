package model;

import view.GamePanel;
import view.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Entity implements Movable {
    private final int speed = 4;
    private int boardX;
    private int boardY;
    public Bomb bomb;
    public ArrayList<Bomb> bombs;
    private long bombTimer;
    private BufferedImage[] images;
    private int imageCounter;
    private int imageNumber;
    private final Hitbox hitbox;
    private HashMap<Direction, Boolean> availableDirections;
    private Direction currentDirection;

    public Player(int x, int y) {
        this.position = new Position(x * 48, y * 48);
        this.boardX = x;
        this.boardY = y;
        this.images = new BufferedImage[8];
        this.hitbox = new Hitbox(this.position.getX() + 12, this.position.getY() + 24, 24, 24);
        this.imageCounter = 0;
        this.imageNumber = 1;

        try {
            BufferedImage image1 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_down_1.png"));
            BufferedImage image2 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_down_2.png"));
            BufferedImage image3 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_up_1.png"));
            BufferedImage image4 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_up_2.png"));
            BufferedImage image5 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_left_1.png"));
            BufferedImage image6 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_left_2.png"));
            BufferedImage image7 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_right_1.png"));
            BufferedImage image8 = ImageIO.read(getClass().getResourceAsStream("/assets/images/boy_right_2.png"));
            images[0] = image1;
            images[1] = image2;
            images[2] = image3;
            images[3] = image4;
            images[4] = image5;
            images[5] = image6;
            images[6] = image7;
            images[7] = image8;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bombs = new ArrayList<>();

        this.availableDirections = new HashMap<>();
        availableDirections.put(Direction.UP, true);
        availableDirections.put(Direction.DOWN, true);
        availableDirections.put(Direction.LEFT, true);
        availableDirections.put(Direction.RIGHT, true);

        this.currentDirection = Direction.DOWN;
    }


    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void move(Direction d) {
        if (d == Direction.UP && availableDirections.get(Direction.UP) ) {
            this.currentDirection = Direction.UP;
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.RIGHT && availableDirections.get(Direction.RIGHT)) {
            this.currentDirection = Direction.RIGHT;
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.UP);
        }
        else if (d == Direction.LEFT && availableDirections.get(Direction.LEFT)) {
            this.currentDirection = Direction.LEFT;
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.UP);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.DOWN && availableDirections.get(Direction.DOWN)) {
            this.currentDirection = Direction.DOWN;
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        }

        imageCounter++;
        if (imageCounter > 10) {
            if (imageNumber == 1) {
                imageNumber = 2;
            } else if (imageNumber == 2) {
                imageNumber = 1;
            }
            imageCounter = 0;
        }
    }

    public void changeBoardPosition(int x, int y) {
        this.boardX = x;
        this.boardY = y;
    }

    private void bombTimer() {
        bombTimer = System.currentTimeMillis();
    }
    public boolean bombHasExploded(Bomb b){
        return System.currentTimeMillis() - bombTimer >= b.getEXPLOSION_DELETE();
    }

    public void placeBomb() {
        bombTimer();
        Bomb bomb = new Bomb(this.position.getX(), this.position.getY());
        this.bombs.add(bomb);
        this.bomb = bomb;
    }

    @Override
    public void draw(Graphics2D g) {
        if(!bombs.isEmpty()) {
            for(Bomb b : bombs){
                b.draw(g);
            }
        }
        super.draw(g);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (this.currentDirection == Direction.UP) {
            if (imageNumber == 1) {
                g2.drawImage(images[2], position.getX(), position.getY(), 48, 48, null);
            } else if (imageNumber == 2) {
                g2.drawImage(images[3], position.getX(), position.getY(), 48, 48, null);
            }
        } else if (this.currentDirection == Direction.DOWN) {
            if (imageNumber == 1) {
                g2.drawImage(images[0], position.getX(), position.getY(), 48, 48, null);
            } else if (imageNumber == 2) {
                g2.drawImage(images[1], position.getX(), position.getY(), 48, 48, null);
            }
        } else if (this.currentDirection == Direction.LEFT) {
            if (imageNumber == 1) {
                g2.drawImage(images[4], position.getX(), position.getY(), 48, 48, null);
            } else if (imageNumber == 2) {
                g2.drawImage(images[5], position.getX(), position.getY(), 48, 48, null);
            }
        } else {
            if (imageNumber == 1) {
                g2.drawImage(images[6], position.getX(), position.getY(), 48, 48, null);
            } else if (imageNumber == 2) {
                g2.drawImage(images[7], position.getX(), position.getY(), 48, 48, null);
            }
        }
    }

    public void disableDirection(Direction d) {
        availableDirections.put(d, false);
    }

    public void enableDirection(Direction d) {
        availableDirections.put(d, true);
    }

    public boolean collidesWith(Entity e) {
        return !(e.getClass() == Floor.class) && this.hitbox.intersects(e.hitbox);
    }

    public ArrayList<Direction> checkCollisionDirectionWith(Entity e) {
        return hitbox.checkCollisionDirection(e.getHitbox());
    }

    public void handleCollisionWith(Entity e) {
        if (e.getClass() == Wall.class && this.collidesWith(e)) {
            ArrayList<Direction> collisionDirections = this.checkCollisionDirectionWith(e);
            if (collisionDirections.contains(Direction.UP)) {
                this.disableDirection(Direction.UP);
            } if (collisionDirections.contains(Direction.DOWN)) {
                this.disableDirection(Direction.DOWN);
            } if (collisionDirections.contains(Direction.RIGHT)) {
                this.disableDirection(Direction.RIGHT);
            } if (collisionDirections.contains(Direction.LEFT)) {
                this.disableDirection(Direction.LEFT);
            }
        }
    }

}