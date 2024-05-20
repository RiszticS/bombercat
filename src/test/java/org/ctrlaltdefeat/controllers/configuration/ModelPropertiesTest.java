package org.ctrlaltdefeat.controllers.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelPropertiesTest {
    private static final int DEFAULT_HITBOX_THICKNESS = 4;
    private static final int DEFAULT_MOVING_ELEMENT_DEFAULT_SPEED = 4;
    private static final int DEFAULT_NUMBER_OF_TYPES_OF_POWER_UPS = 2;

    @Test
    void getHitboxThickness() {
        int hitboxThickness = ModelProperties.getHitboxThickness();
        assertEquals(DEFAULT_HITBOX_THICKNESS, hitboxThickness);
    }

    @Test
    void getMovingElementDefaultSpeed() {
        int defaultSpeed = ModelProperties.getMovingElementDefaultSpeed();
        assertEquals(DEFAULT_MOVING_ELEMENT_DEFAULT_SPEED, defaultSpeed);
    }

    @Test
    void getNumberOfTypesOfPowerUps() {
        int numOfPowerUps = ModelProperties.getNumberOfTypesOfPowerUps();
        assertEquals(DEFAULT_NUMBER_OF_TYPES_OF_POWER_UPS, numOfPowerUps);
    }
}
