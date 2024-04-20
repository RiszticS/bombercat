package main.controllers.configuration;

import main.controllers.movement.PlayerControls;

public class ControlsProperties {
    private static final ConfigurationManager cm = new ConfigurationManager("controls.properties");

    public static PlayerControls getPlayerControls(int playerNumber) {
        return new PlayerControls(
                Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_up")),
                Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_down")),
                Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_left")),
                Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_right")),
                Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_bomb"))
        );
    }
}
