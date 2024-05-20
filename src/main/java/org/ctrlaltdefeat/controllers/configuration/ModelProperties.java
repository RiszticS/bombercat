package org.ctrlaltdefeat.controllers.configuration;

public class ModelProperties {
    private static final ConfigurationManager cm = new ConfigurationManager("model.properties");

    public static int getHitboxThickness() {
        return Integer.parseInt(cm.getProperty("hitbox_thickness"));
    }

    public static int getMovingElementDefaultSpeed() {
        return Integer.parseInt(cm.getProperty("moving_element_default_speed"));
    }

    public static int getNumberOfTypesOfPowerUps() {
        return Integer.parseInt(cm.getProperty("number_of_types_of_power_ups"));
    }
}
