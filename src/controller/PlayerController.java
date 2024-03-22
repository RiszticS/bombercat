package controller;

import model.Direction;
import model.GameModel;
import model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private final Player player;
    private final int up, down, left, right, placeBomb;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public PlayerController(Player player, ControlSet controlSet) {
        this.player = player;
        this.up = controlSet.getUp();
        this.down = controlSet.getDown();
        this.left = controlSet.getLeft();
        this.right = controlSet.getRight();
        this.placeBomb = controlSet.getPlaceBomb();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == up) {
            upPressed = true;
        }  else if (code == down) {
            downPressed = true;
        }  else if (code == left) {
            leftPressed = true;
        }  else if (code == right) {
            rightPressed = true;
        }  else if (code == placeBomb) {
            player.placeBomb();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == up) {
            upPressed = false;
        }  else if (code == down) {
            downPressed = false;
        }  else if (code == left) {
            leftPressed = false;
        }  else if (code == right) {
            rightPressed = false;
        }
    }

    public void updatePlayer() {
        if (this.upPressed) {
            player.move(Direction.UP);
        }  else if (this.downPressed) {
            player.move(Direction.DOWN);
        }  else if (this.leftPressed) {
            player.move(Direction.LEFT);
        }  else if (this.rightPressed) {
            player.move(Direction.RIGHT);
        }
    }
}
