package org.ctrlaltdefeat.view.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScoreBoardPanel extends JPanel {
    public ScoreBoardPanel(ArrayList<Integer> scores) {
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        //this.setLayout(new GridLayout(scores.size(),1));
        for (int i = 0; i < scores.size(); i++) {
            JLabel player = new JLabel("Player" + (i+1) + ": " + scores.get(i) + " points");
            player.setFont(new Font("Serif", Font.PLAIN, 18));
            this.add(player);
        }
    }


}
