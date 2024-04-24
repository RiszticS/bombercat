package main.view.game;

import main.controllers.game.GameLoop;
import main.model.GameModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EndGameWindow extends JFrame {
    public EndGameWindow(ArrayList<Integer> scores) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(new EndGamePanel(scores));
        this.setResizable(false);
        this.setTitle("Game over");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
