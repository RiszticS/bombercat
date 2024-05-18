package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.MatrixPosition;

public class Wall extends FixedElement {
    public Wall(MatrixPosition p,String type) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        GraphicsController.addManager(new StaticGraphics("/images/tiles/spacestation/"+type+".png",
                this.position.convertToCoordinatePosition(tileSize),
                tileSize));
    }

    @Override
    public String getType() {
        return "Wall";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
