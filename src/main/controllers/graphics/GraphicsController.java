package main.controllers.graphics;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsController {
    private static final ArrayList<GraphicsManager> managers = new ArrayList<>();

    public static void addManager(GraphicsManager gm) {
        GraphicsController.managers.add(gm);
    }

    public static void draw(Graphics2D g2) {
        for(GraphicsManager gm : managers) {
            gm.draw(g2);
        }
    }
}
