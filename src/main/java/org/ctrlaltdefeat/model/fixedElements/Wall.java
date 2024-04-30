package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public class Wall extends FixedElement {
    public Wall(MatrixPosition p) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        GraphicsController.addManager(new StaticGraphics("/images/wall.png",
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
