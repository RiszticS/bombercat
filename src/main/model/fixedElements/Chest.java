package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.model.positions.CoordinatePosition;
import main.model.positions.MatrixPosition;
import main.model.positions.Position;
import main.model.Hitbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Chest extends FixedElement {
    private BufferedImage image;
    private PowerUp powerUp;

    public Chest(MatrixPosition p) {
        super(p);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/main/assets/images/chest.png"));
        } catch (IOException e) {
            this.image =  null;
            System.out.println("Chest image could not be found!");
        }
        this.powerUp = null;
    }

    public void explode(FixedElement[][] board) {
        if (this.powerUp != null) {
            board[this.position.getX()][this.position.getY()] = this.powerUp;
        } else {
            board[this.position.getX()][this.position.getY()] = new EmptyTile(this.position);
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
        return "Chest";
    }

    @Override
    public void update(FixedElement[][] board) {

    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
        this.powerUp.setPosition(this.position);
    }
}
