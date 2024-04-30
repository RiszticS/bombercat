package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public class EmptyTile extends FixedElement {
    public EmptyTile(MatrixPosition p) {
        super(p);
        int tileSize = GraphicProperties.getTileSize();
        GraphicsController.addManagerFirst(new StaticGraphics("/images/floor.png", p.convertToCoordinatePosition(tileSize),tileSize));
    }

    @Override
    public String getType() {
        return "EmptyTile";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}
