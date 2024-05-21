package org.ctrlaltdefeat.model.positions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixPositionTest {

    @Test
    void convertToCoordinatePosition() {
        MatrixPosition position = new MatrixPosition(3, 3);
        CoordinatePosition pos2 = position.convertToCoordinatePosition(2);
        Assertions.assertEquals(6, pos2.getX());
        Assertions.assertEquals(6, pos2.getY());
    }
}