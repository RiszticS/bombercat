package org.ctrlaltdefeat.model.fixedElements;

import org.ctrlaltdefeat.model.positions.MatrixPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedElementTest {
    @Test
    void getPosition() {
        Chest c = new Chest(new MatrixPosition(0, 0));

        MatrixPosition position = c.getPosition();
        assertEquals(0, position.getX());
        assertEquals(0, position.getY());
    }

    @Test
    void setPosition() {
        Chest c = new Chest(new MatrixPosition(0, 0));
        c.setPosition(new MatrixPosition(1, 1));
        MatrixPosition position = c.getPosition();
        assertEquals(1, position.getX());
        assertEquals(1, position.getY());
    }
}