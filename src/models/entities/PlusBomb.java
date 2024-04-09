package models.entities;

import javax.imageio.ImageIO;
import java.io.IOException;

public class PlusBomb extends PowerUp{

    public PlusBomb(int x, int y) {
        super(x,y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/plusBomb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
