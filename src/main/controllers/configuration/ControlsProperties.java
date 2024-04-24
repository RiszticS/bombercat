package main.controllers.configuration;

import main.controllers.movement.PlayerControls;

import java.util.ArrayList;

public class ControlsProperties {
    private static final ConfigurationManager cm = new ConfigurationManager("controls.properties");

    public static PlayerControls getPlayerControls(int playerNumber) {
        int up = Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_up"));
        int down = Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_down"));
        int left = Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_left"));
        int right = Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_right"));
        int bomb = Integer.parseInt(cm.getProperty("player" + (char) (playerNumber + '0') + "_bomb"));

        return new PlayerControls(
                up,
                down,
                left,
                right,
                bomb
        );
    }
}
