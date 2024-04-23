package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.game.RenderTimer;
import main.model.Hitbox;
import main.model.positions.CoordinatePosition;
import main.model.positions.Direction;
import main.model.positions.MatrixPosition;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Flare extends FixedElement {
    private BufferedImage image;
    private final Direction direction;
    private final RenderTimer dissipationCountdown;

    public Flare(MatrixPosition position, Direction direction) {
        super(position);
        this.direction = direction;
        int tileSize = GraphicProperties.getTileSize();
        if (direction == Direction.UP) {
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/flareup.png"));
            } catch(IOException e) {
                this.image =  null;
                System.out.println("Explosion image could not be found!");
            }
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), (tileSize * 7) / 12, tileSize, (tileSize - ((tileSize * 7) / 12)) / 2, 0);
        } else if (direction == Direction.DOWN) {
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/flaredown.png"));
            } catch(IOException e) {
                this.image =  null;
                System.out.println("Explosion image could not be found!");
            }
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), (tileSize * 7) / 12, tileSize, (tileSize - ((tileSize * 7) / 12)) / 2, 0);
        } else if (direction == Direction.LEFT) {
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/flareleft.png"));
            } catch(IOException e) {
                this.image =  null;
                System.out.println("Explosion image could not be found!");
            }
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), tileSize, (tileSize * 7) / 12, 0, (tileSize - ((tileSize * 7) / 12)) / 2);
        } else if (direction == Direction.RIGHT) {
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/flareright.png"));
            } catch(IOException e) {
                this.image =  null;
                System.out.println("Explosion image could not be found!");
            }
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), tileSize, (tileSize * 7) / 12, 0, (tileSize - ((tileSize * 7) / 12)) / 2);
        } else {
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize));
        }

        dissipationCountdown = new RenderTimer(180);
        dissipationCountdown.start();

    }

    public void dissipate(FixedElement[][] board) {
        board[position.getX()][position.getY()] = new EmptyTile(this.position);
    }

    @Override
    public void update(FixedElement[][] board) {
        if (dissipationCountdown.finished()) {
            dissipate(board);
        } else {
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
        return "Flare";
    }

    public Direction getDirection() {
        return direction;
    }
}
