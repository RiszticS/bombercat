package org.ctrlaltdefeat.model.positions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void up() {
        assertEquals(0, Direction.UP.getIndex());
    }

    @Test
    void down() {
        assertEquals(2, Direction.DOWN.getIndex());
    }

    @Test
    void left() {
        assertEquals(3, Direction.LEFT.getIndex());
    }

    @Test
    void right() {
        assertEquals(1, Direction.RIGHT.getIndex());
    }

    @Test
    void idle() {
        assertEquals(4, Direction.IDLE.getIndex());
    }
}