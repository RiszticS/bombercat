package org.ctrlaltdefeat.controllers.graphics;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsController {
    private static ArrayList<GraphicsManager> managers = new ArrayList<>();
    private static ArrayList<GraphicsManager> removeQueue = new ArrayList<>();
    public static void addManagerFirst(GraphicsManager gm) {
        managers.addFirst(gm);
    }
    public static void addManager(GraphicsManager gm) {
        if (!managers.contains(gm))
            managers.add(gm);
    }
    public static void removeManager(GraphicsManager gm) {
        removeQueue.add(gm);
    }


    public static void reset() {
        managers = new ArrayList<>();
    }

    public static void draw(Graphics2D g2) {
        for(GraphicsManager gm : managers) {
            gm.draw(g2);
        }

        if (!removeQueue.isEmpty()) {
            for (GraphicsManager gm : removeQueue) {
                managers.remove(gm);
            }
        }
    }
}