package org.ctrlaltdefeat.view.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndGamePanel extends JPanel {
    public EndGamePanel(ArrayList<Integer> scores) {
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        JLabel title = new JLabel("Game over");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        this.add(title);
        for (int i = 0; i < scores.size(); i++) {
            JLabel player = new JLabel("Player" + (i+1) + ": " + scores.get(i) + " points");
            player.setFont(new Font("Serif", Font.PLAIN, 18));
            this.add(player);
        }
    }
}
