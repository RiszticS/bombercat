package org.ctrlaltdefeat.model.positions;

import java.util.ArrayList;
import java.util.Random;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3),
    IDLE(4),
    DIE(5);


    private int index;
    private static final Random random = new Random();
    private Direction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns a random Direction that is not Idle.
     * @return A Direction object: UP, DOWN, LEFT, or RIGHT
     */
    public static Direction randomDirection() {
        ArrayList<Direction> directions = new ArrayList<>();
        for (Direction d : Direction.values()) {
            if (d != Direction.IDLE) {
                directions.add(d);
            }
        }
        return directions.get(random.nextInt(directions.size()));
    }

    /**
     * Return a random Direction from the ArrayList that is provided as a parameter.
     * @param availableDirections The list of Directions to randomly pick a Direction from.
     * @return The randomly selected Direction object.
     */
    public static Direction randomDirection(ArrayList<Direction> availableDirections) {
        if (availableDirections.size() == 0) {
            return Direction.UP;
        }
        return availableDirections.get(random.nextInt(availableDirections.size()));
    }
}
