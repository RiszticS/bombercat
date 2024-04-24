package main.view.game;

import main.controllers.game.GameLoop;
import main.model.GameModel;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ScoreBoardWindow extends JFrame {

    public ScoreBoardWindow(ArrayList<Integer> scores, GameLoop gameLoop, GameModel gameModel, GamePanel gamePanel) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                gameModel.resetLevel(gamePanel);
                gameModel.nextRound();
                gameLoop.startGame();
            }
        });
        this.setResizable(false);
        this.setTitle("Scores");
        this.setSize(200, 200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
