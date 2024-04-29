package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.game.RenderTimer;
import main.model.movingElements.Player;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PowerUpSpeedReducer extends PowerUp {
    public PowerUpSpeedReducer(MatrixPosition p) {
        super(p);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/minusPowerUp.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(Player p) {
        int originalSpeed = p.getSpeed();
        int reducedSpeed = originalSpeed / 2;
        p.setSpeed(reducedSpeed);
        p.getEffectTimer().start();

        if (p.getEffectTimer().finished()) {
            p.setSpeed(originalSpeed);
        } else {
            p.getEffectTimer().decrease();
        }
    }

    @Override
    public void update(FixedElement[][] board) {
        super.update(board);
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
}

