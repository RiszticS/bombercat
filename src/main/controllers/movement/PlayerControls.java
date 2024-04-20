package main.controllers.movement;

public class PlayerControls {
    private final int up, down, left, right, bomb;
    public PlayerControls(int up, int down, int left, int right, int bomb) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.bomb = bomb;
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

    public int getBomb() {
        return bomb;
    }
}
