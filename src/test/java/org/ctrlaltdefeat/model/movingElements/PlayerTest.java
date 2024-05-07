package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.model.positions.CoordinatePosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ctrlaltdefeat.controllers.graphics.GraphicsController;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getId() {
        CoordinatePosition position = new CoordinatePosition(0, 0);
        Player player = new Player(position);
        int expectedId = 1;

        Assertions.assertEquals(expectedId, player.getId());
    }
}