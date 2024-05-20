package org.ctrlaltdefeat.model.movingElements;

import org.ctrlaltdefeat.model.fixedElements.Bomb;
import org.ctrlaltdefeat.model.fixedElements.FixedElement;
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
        MatrixPosition m = new MatrixPosition(10, 20);
        FixedElement[][] board = new FixedElement[15][15];

        Player player = new Player(m, board, 0);
        int playerId = player.getId();

        assertEquals(1, playerId, "Player ID should match");
    }

    @Test
    @DisplayName("Test getBombs() method")
    void getBombs() {
        MatrixPosition m = new MatrixPosition(0, 0);
        FixedElement[][] board = new FixedElement[15][15];
        Player player = new Player(m, board, 0);

        ArrayDeque<Bomb> bombs = player.getBombs();

        assertNotNull(bombs, "Bombs deque should not be null");
        assertFalse(bombs.isEmpty(), "Bombs deque shouldn't be empty initially");

        bombs.add(new Bomb(new MatrixPosition(1, 1)));

        ArrayDeque<Bomb> updatedBombs = player.getBombs();
        int count = updatedBombs.size();
        assertEquals(2, count, "Bombs deque should contain two bomb after addition");
    }
}