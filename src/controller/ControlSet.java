package controller;

import java.awt.event.KeyEvent;

public enum ControlSet {
    WASDX(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Y),
    IJKLM(KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_M),
    ARROWSANDSPACE(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);

    private final int up, down, left, right, placeBomb;
    private ControlSet(int up, int down, int left, int right, int placeBomb) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.placeBomb = placeBomb;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getPlaceBomb() {
        return placeBomb;
    }
}
