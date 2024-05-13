package main.model.fixedElements;

import main.controllers.configuration.GraphicProperties;
import main.controllers.graphics.GraphicsController;
import main.controllers.graphics.StaticGraphics;
import main.model.positions.MatrixPosition;

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
