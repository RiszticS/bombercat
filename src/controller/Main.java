package controller;

import model.GameModel;
import view.GameWindow;

public class Main {
    public static void main(String[] args) {

        // MenuWindow menuWindow = new MenuWindow();
        GameModel gm = new GameModel(2);
        GameWindow gw = new GameWindow(gm);
        GameLoop gc = new GameLoop(gm, gw.getGamePanel());
        gc.start();

    }
}
