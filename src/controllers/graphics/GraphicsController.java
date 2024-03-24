package controllers.graphics;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsController implements GraphicsManager{
    private final ArrayList<GraphicsManager> managers;

    public GraphicsController() {
        this.managers = new ArrayList<>();
    }

    public void addManager(GraphicsManager gm) {
        this.managers.add(gm);
    }

    public void draw(Graphics2D g2, int x, int y) {
        for(GraphicsManager gm : managers) {
            gm.draw(g2, x, y);
        }
    }
}
