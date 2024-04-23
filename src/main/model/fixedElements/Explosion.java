package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.game.RenderTimer;
import main.model.positions.CoordinatePosition;
import main.model.positions.Direction;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Explosion extends FixedElement {
    private BufferedImage image;
    private int radius;
    private int spreadLevel;
    private RenderTimer spreadCountdown;
    private RenderTimer dissipationCountdown;
    private boolean canSpreadUpwards, canSpreadDownwards, canSpreadLeft, canSpreadRight;


    public Explosion(MatrixPosition p, Bomb bomb) {
        super(p);
        this.radius = bomb.getExplosionStrength();
        this.spreadLevel = 0;
        spreadCountdown = new RenderTimer(15);
        dissipationCountdown = new RenderTimer(180);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/explosion.png"));
        } catch(IOException e) {
            this.image =  null;
            System.out.println("Explosion image could not be found!");
        }
        canSpreadUpwards = true;
        canSpreadDownwards = true;
        canSpreadLeft = true;
        canSpreadRight = true;
    }

    public void spread(FixedElement[][] board) {
        if (spreadLevel == 0) {
            board[position.getX()][position.getY()] = this;
            dissipationCountdown.start();
        } else {
            checkCurrentSpreadLevel(board);
            if (canSpreadUpwards) {
                MatrixPosition p = new MatrixPosition(position.getX() - spreadLevel, position.getY());
                board[position.getX() - spreadLevel][position.getY()] = new Flare(p, Direction.UP);
            }
            if (canSpreadDownwards) {
                MatrixPosition p = new MatrixPosition(position.getX() + spreadLevel, position.getY());
                board[position.getX() + spreadLevel][position.getY()] = new Flare(p, Direction.DOWN);
            }
            if (canSpreadLeft) {
                MatrixPosition p = new MatrixPosition(position.getX(), position.getY() - spreadLevel);
                board[position.getX()][position.getY() - spreadLevel] = new Flare(p, Direction.LEFT);
            }
            if (canSpreadRight) {
                MatrixPosition p = new MatrixPosition(position.getX(), position.getY() + spreadLevel);
                board[position.getX()][position.getY() + spreadLevel] = new Flare(p, Direction.RIGHT);
            }
        }
        System.out.println("SpreadLevel: " + spreadLevel);
        spreadCountdown.start();
        spreadLevel++;
    }

    public void dissipate(FixedElement[][] board) {
        board[position.getX()][position.getY()] = new EmptyTile(position);
        canSpreadUpwards = true;
        canSpreadDownwards = true;
        canSpreadLeft = true;
        canSpreadRight = true;
    }

    @Override
    public void update(FixedElement[][] board) {
        if (spreadCountdown.finished() && spreadLevel <= radius) {
            spread(board);
        } else if (!spreadCountdown.finished() && spreadLevel <= radius) {
            spreadCountdown.decrease();
        }
        if (dissipationCountdown.finished()) {
            dissipate(board);
        } else if (!dissipationCountdown.finished()) {
            dissipationCountdown.decrease();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int tileSize = GraphicProperties.getTileSize();
        CoordinatePosition p = position.convertToCoordinatePosition(tileSize);
        g2.drawImage(image, p.getX(), p.getY(),tileSize, tileSize, null);
        g2.setColor(Color.RED);
        g2.drawRect(p.getX(), p.getY(),tileSize, tileSize);
        hitbox.draw(g2);
    }

    @Override
    public String getType() {
        return "Explosion";
    }

    public void setPosition(MatrixPosition position) {
        this.position = position;
    }

    private void checkCurrentSpreadLevel(FixedElement[][] board) {
        if (position.getX() - spreadLevel < 0) {
            canSpreadUpwards = false;
        } else {
            if (board[position.getX() - spreadLevel][position.getY()].getType().equals("Wall")) {
                canSpreadUpwards = false;
            } else if (board[position.getX() - spreadLevel][position.getY()].getType().equals("Chest")) {
                canSpreadUpwards = false;
                ((Chest)board[position.getX() - spreadLevel][position.getY()]).explode();
            } else if (board[position.getX() - spreadLevel][position.getY()].getType().equals("Bomb")) {
                canSpreadUpwards = false;
                Bomb bomb = ((Bomb)board[position.getX() - spreadLevel][position.getY()]);
                bomb.getExplosionCountdown().reset();
                bomb.update(board);
            } else if (board[position.getX() - spreadLevel][position.getY()].getType().equals("Explosion")) {
                canSpreadUpwards = false;
            }
        }

        if (position.getX() + spreadLevel >= board.length) {
            canSpreadDownwards = false;
        } else {
            if (board[position.getX() + spreadLevel][position.getY()].getType().equals("Wall")) {
                canSpreadDownwards = false;
            } else if (board[position.getX() + spreadLevel][position.getY()].getType().equals("Chest")) {
                canSpreadDownwards = false;
                ((Chest)board[position.getX() + spreadLevel][position.getY()]).explode();
            } else if (board[position.getX() + spreadLevel][position.getY()].getType().equals("Bomb")) {
                canSpreadDownwards = false;
                Bomb bomb = ((Bomb)board[position.getX() + spreadLevel][position.getY()]);
                bomb.getExplosionCountdown().reset();
                bomb.update(board);
            } else if (board[position.getX() + spreadLevel][position.getY()].getType().equals("Explosion")) {
                canSpreadDownwards = false;
            }
        }

        if (position.getY() - spreadLevel < 0) {
            canSpreadLeft = false;
        } else {
            if (board[position.getX()][position.getY() - spreadLevel].getType().equals("Wall")) {
                canSpreadLeft = false;
            } else if (board[position.getX()][position.getY() - spreadLevel].getType().equals("Chest")) {
                canSpreadLeft = false;
                ((Chest)board[position.getX()][position.getY() - spreadLevel]).explode();
            } else if (board[position.getX()][position.getY() - spreadLevel].getType().equals("Bomb")) {
                canSpreadLeft = false;
                Bomb bomb = ((Bomb)board[position.getX()][position.getY() - spreadLevel]);
                bomb.getExplosionCountdown().reset();
                bomb.update(board);
            } else if (board[position.getX()][position.getY() - spreadLevel].getType().equals("Explosion")) {
                canSpreadLeft = false;
            }
        }

        if (position.getY() + spreadLevel >= board.length) {
            canSpreadRight = false;
        } else {
            if (board[position.getX()][position.getY() + spreadLevel].getType().equals("Wall")) {
                canSpreadRight = false;
            } else if (board[position.getX()][position.getY() + spreadLevel].getType().equals("Chest")) {
                canSpreadRight = false;
                ((Chest)board[position.getX()][position.getY() + spreadLevel]).explode();
            } else if (board[position.getX()][position.getY() + spreadLevel].getType().equals("Bomb")) {
                canSpreadRight = false;
                Bomb bomb = ((Bomb)board[position.getX()][position.getY() + spreadLevel]);
                bomb.getExplosionCountdown().reset();
                bomb.update(board);
            } else if (board[position.getX()][position.getY() + spreadLevel].getType().equals("Explosion")) {
                canSpreadRight = false;
            }
        }
    }
}
