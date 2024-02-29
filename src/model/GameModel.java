package model;

import controller.ControlSet;

import java.util.ArrayList;

public class GameModel {
    private final ArrayList<Player> players;

    public GameModel(int numberOfPlayers) {
        this.players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }


}
