package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.game.RenderTimer;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.Hitbox;
import org.ctrlaltdefeat.model.positions.Direction;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.awt.image.BufferedImage;

public class Flare extends FixedElement {
    private BufferedImage image;
    private final Direction direction;
    private final RenderTimer dissipationCountdown;
    private final StaticGraphics sg;

    public Flare(MatrixPosition position, Direction direction) {
        super(position);
        this.direction = direction;
        int tileSize = GraphicProperties.getTileSize();
        String img = "";
        if (direction == Direction.UP) {
            img = "up";
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), (tileSize * 7) / 12, tileSize, (tileSize - ((tileSize * 7) / 12)) / 2, 0);
        } else if (direction == Direction.DOWN) {
            img = "down";
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), (tileSize * 7) / 12, tileSize, (tileSize - ((tileSize * 7) / 12)) / 2, 0);
        } else if (direction == Direction.LEFT) {
            img = "left";
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), tileSize, (tileSize * 7) / 12, 0, (tileSize - ((tileSize * 7) / 12)) / 2);
        } else if (direction == Direction.RIGHT) {
            img = "right";
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize), tileSize, (tileSize * 7) / 12, 0, (tileSize - ((tileSize * 7) / 12)) / 2);
        } else {
            this.hitbox = new Hitbox(this.position.convertToCoordinatePosition(tileSize));
        }
        sg = new StaticGraphics("/images/flare" + img + ".png", position.convertToCoordinatePosition(tileSize), tileSize);
        GraphicsController.addManager(sg);

        dissipationCountdown = new RenderTimer(180);
        dissipationCountdown.start();

    }

    public void dissipate(FixedElement[][] board) {
        GraphicsController.removeManager(sg);
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
    public String getType() {
        return "Flare";
    }

    public Direction getDirection() {
        return direction;
    }
}
