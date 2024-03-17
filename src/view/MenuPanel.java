package view;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public Rectangle newGameButton = new Rectangle(720 / 2 + 120, 150, 100, 50);
    public Rectangle mapEditorButton = new Rectangle(720 / 2 + 120, 250, 100, 50);
    public Rectangle exitButton = new Rectangle(720 / 2 + 120, 350, 100, 50);

    public MenuPanel(){
        this.setPreferredSize(new Dimension(720, 720));
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font fnt = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt);
        g.setColor(Color.blue);
        g.drawString("Multiplayer Bomberman", 720 / 2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("New Game", newGameButton.x + 19, newGameButton.y + 33);
        g2d.draw(newGameButton);
        g.drawString("Map Editor", mapEditorButton.x + 19, mapEditorButton.y + 33);
        g2d.draw(mapEditorButton);
        g.drawString("Exit", exitButton.x + 19, exitButton.y + 33);
        g2d.draw(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
}
