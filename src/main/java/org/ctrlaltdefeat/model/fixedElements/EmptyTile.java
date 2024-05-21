package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;
import org.ctrlaltdefeat.controllers.graphics.StaticGraphics;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

public class EmptyTile extends FixedElement {
    private StaticGraphics sg;
    public EmptyTile(MatrixPosition p, boolean addRender) {
        super(p);
        if (addRender) {
            int tileSize = GraphicProperties.getTileSize();
            sg = new StaticGraphics("/images/tiles/spacestation/floor.png", p.convertToCoordinatePosition(tileSize),tileSize);
            GraphicsController.addManagerFirst(sg);
        }
    }

    @Override
    public String getType() {
        return "EmptyTile";
    }

    @Override
    public void update(FixedElement[][] board) {

    }
}

