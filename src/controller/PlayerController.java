package controller;

import model.Direction;
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
        }  if (code == down) {
            downPressed = true;
        }  if (code == left) {
            leftPressed = true;
        }  if (code == right) {
            rightPressed = true;
        }  if (code == placeBomb) {
            player.placeBomb();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == up) {
            upPressed = false;
        }  if (code == down) {
            downPressed = false;
        }  if (code == left) {
            leftPressed = false;
        }  if (code == right) {
            rightPressed = false;
        }
    }

    public void updatePlayer() {
        if (this.upPressed) {
            player.move(Direction.UP);
        }  if (this.downPressed) {
            player.move(Direction.DOWN);
        }  if (this.leftPressed) {
            player.move(Direction.LEFT);
        }  if (this.rightPressed) {
            player.move(Direction.RIGHT);
        }
    }
}
