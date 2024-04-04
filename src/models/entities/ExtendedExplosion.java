package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ExtendedExplosion extends PowerUp{
    public ExtendedExplosion(int x, int y) {
        super(x,y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/images/extendedExplosion.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
