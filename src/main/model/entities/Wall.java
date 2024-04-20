package main.model.entities;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.Position;

public class Wall extends Entity {

    public Wall(int x, int y) {

        this.position = new Position(x * GraphicProperties.getTileSize(), y * GraphicProperties.getTileSize());
        this.hitbox = new Hitbox(this.position.getX(), this.position.getY(), GraphicProperties.getTileSize(), GraphicProperties.getTileSize());
        GraphicsController.addManager(new StaticGraphics("/main/assets/images/wall.png", this.position, GraphicProperties.getTileSize(), GraphicProperties.getTileSize()));
    }
}
