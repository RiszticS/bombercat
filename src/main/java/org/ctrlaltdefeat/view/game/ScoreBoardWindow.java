package org.ctrlaltdefeat.view.game;

import org.ctrlaltdefeat.controllers.game.GameLoop;
import org.ctrlaltdefeat.model.GameModel;

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
                gameModel.resetLevel(gamePanel);
                //gameModel.nextRound();
                gameLoop.startGame();
            }
        });
        this.getContentPane().add(new ScoreBoardPanel(scores));
        this.setResizable(false);
        this.setTitle("Scores");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
