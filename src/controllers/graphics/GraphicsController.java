package controllers.graphics;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsController {
    private final ArrayList<GraphicsManager> managers;

    public GraphicsController() {
        this.managers = new ArrayList<>();
    }

    public void addManager(GraphicsManager gm) {
        this.managers.add(gm);
    }

    public void draw(Graphics2D g2) {
        for(GraphicsManager gm : managers) {
            gm.draw(g2);
        }
    }
}
