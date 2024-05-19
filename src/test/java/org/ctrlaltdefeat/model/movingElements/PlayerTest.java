package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.model.fixedElements.Bomb;
import org.ctrlaltdefeat.model.positions.CoordinatePosition;
import org.ctrlaltdefeat.model.positions.MatrixPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("Get ID of player")
    void getId() {
        CoordinatePosition p = new CoordinatePosition(10, 20);

        Player player = new Player(p);
        int playerId = player.getId();

        assertEquals(1, playerId, "Player ID should match");
    }

    @Test
    @DisplayName("Test getBombs() method")
    void getBombs() {
        CoordinatePosition position = new CoordinatePosition(0, 0);
        Player player = new Player(position);

        ArrayDeque<Bomb> bombs = player.getBombs();

        assertNotNull(bombs, "Bombs deque should not be null");
        assertTrue(bombs.isEmpty(), "Bombs deque should be empty initially");

        bombs.add(new Bomb(new MatrixPosition(1, 1)));

        ArrayDeque<Bomb> updatedBombs = player.getBombs();
        int count = updatedBombs.size();
        assertEquals(1, count, "Bombs deque should contain one bomb after addition");
    }
}