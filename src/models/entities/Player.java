package models.entities;

import controllers.configuration.GraphicProperties;
import controllers.graphics.AnimationConfiguration;
import controllers.graphics.GraphicsController;
import controllers.graphics.MovingAnimationGraphics;
import models.Direction;
import models.Position;
import models.Movable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Entity implements Movable {
    private final int speed = 4;
    private Bomb bomb;
    private ArrayList<Bomb> bombs;
    private int placedBombs;
    private int bombCounter;
    private int bombRadius;
    private BufferedImage[] images;
    private int imageCounter;
    private int imageNumber;
    private final Hitbox hitbox;
    private HashMap<Direction, Boolean> availableDirections;
    private Direction currentDirection;
    private MovingAnimationGraphics graphicsManager;
    public Player(int x, int y) {
        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        this.boardX = x;
        this.boardY = y;
        this.images = new BufferedImage[8];
        this.hitbox = new Hitbox(this.position.getX() + 5, this.position.getY() + ((int)Math.round(48 * 2 * (GraphicProperties.getTileSize() / 64.0)) - GraphicProperties.getTileSize()), (int)Math.round(GraphicProperties.getTileSize() * 0.95), (int)Math.round(GraphicProperties.getTileSize() * 0.95 ));
        this.imageCounter = 0;
        this.imageNumber = 1;

        ArrayList<AnimationConfiguration> animationConfiguration = new ArrayList<>();
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkback.png", 9, 1, 9, 0, 32, 48, 5));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkright.png", 6, 1, 6, 0, 32, 48, 6));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkfront.png", 9, 1, 9, 0, 32, 48, 5));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautwalkleft.png", 6, 1, 6, 0, 32, 48, 6));
        animationConfiguration.add(new AnimationConfiguration("/assets/images/astronautidle.png", 13, 1, 13, 0, 32, 48, 5));
        graphicsManager = new MovingAnimationGraphics(animationConfiguration, this.position);

        GraphicsController.addManager(this.graphicsManager);

        this.bombs = new ArrayList<>();
        this.placedBombs = 0;
        this.bombCounter = 1;
        this.bombRadius = 1;

        this.availableDirections = new HashMap<>();
        availableDirections.put(Direction.UP, true);
        availableDirections.put(Direction.DOWN, true);
        availableDirections.put(Direction.LEFT, true);
        availableDirections.put(Direction.RIGHT, true);

        this.currentDirection = Direction.DOWN;
    }




    public void move(Direction d) {
        if (d == Direction.UP && availableDirections.get(Direction.UP) ) {
            graphicsManager.changeDirection(d);
            this.position.changeY(-speed);
            this.hitbox.changeY(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.RIGHT && availableDirections.get(Direction.RIGHT)) {
            graphicsManager.changeDirection(d);
            this.position.changeX(speed);
            this.hitbox.changeX(speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.UP);
        }
        else if (d == Direction.LEFT && availableDirections.get(Direction.LEFT)) {
            graphicsManager.changeDirection(d);
            this.position.changeX(-speed);
            this.hitbox.changeX(-speed);
            enableDirection(Direction.DOWN);
            enableDirection(Direction.UP);
            enableDirection(Direction.RIGHT);
        }
        else if (d == Direction.DOWN && availableDirections.get(Direction.DOWN)) {
            graphicsManager.changeDirection(d);
            this.position.changeY(speed);
            this.hitbox.changeY(speed);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
        } else {
            graphicsManager.changeDirection(Direction.IDLE);
            enableDirection(Direction.UP);
            enableDirection(Direction.LEFT);
            enableDirection(Direction.RIGHT);
            enableDirection(Direction.DOWN);
        }
    }

    public void placeBomb() {
        Bomb bomb = new Bomb(((this.hitbox.getX() + 28) / GraphicProperties.getTileSize()) * GraphicProperties.getTileSize(), ((this.hitbox.getY() + 28) / GraphicProperties.getTileSize()) * GraphicProperties.getTileSize(), bombRadius);
        if(bombs.size() < bombCounter){
            this.bombs.add(bomb);
            this.bomb = bomb;
            placedBombs++;
        }
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void addPlusBombs(){
        bombCounter++;
    }
    public void addExtendedExplosion(){
        bombRadius++;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!bombs.isEmpty()) {
            for (int i = 0; i < bombs.size(); i++) {
                if (bombs.get(i) != null) {
                    bombs.get(i).draw(g2);
                    if(bombs.get(i).hasDeleted()){
                        bombs.set(i, null);
                        bombs.remove(i);
                        placedBombs = 0;
                    }
                }
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
        if ((e.getClass() == Wall.class || e.getClass() == Bomb.class || e.getClass() == Chest.class) && this.collidesWith(e)) {
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

    public void handleCollisionWithPowerUps(PowerUp pu){
        if (this.collidesWith(pu) && pu.getClass() == PlusBomb.class && !pu.isPickedUp()){
            addPlusBombs();
            pu.setPickedUp(true);
        }
        if (this.collidesWith(pu) && pu.getClass() == ExtendedExplosion.class && !pu.isPickedUp()){
            addExtendedExplosion();
            pu.setPickedUp(true);
        }
    }

    public void setIdle() {
        graphicsManager.changeDirection(Direction.IDLE);
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

}