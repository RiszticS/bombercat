package main.model;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3),
    IDLE(4);

    private int index;
    private Direction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
