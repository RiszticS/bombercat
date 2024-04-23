package main.controllers.configuration;

public class ModelProperties {
    private static final ConfigurationManager cm = new ConfigurationManager("model.properties");

    public static int getHitboxThickness() {
        return Integer.parseInt(cm.getProperty("hitbox_thickness"));
    }

    public static int getMovingElementDefaultSpeed() {
        return Integer.parseInt(cm.getProperty("moving_element_default_speed"));
    }

    public static int getMonsterDirectionChangeFrequency() {
        return Integer.parseInt(cm.getProperty("monster_direction_change_frequency"));
    }

    public static int getBombCountdownStart() {
        return Integer.parseInt(cm.getProperty("bomb_countdown_start"));
    }
}
