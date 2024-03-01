package model;

public class Monster {
    private final Position position;
    private final int speed;

    public Monster() {
        position = new Position(100, 100);
        speed = 4;
    }

    public void move(Direction d) {
        if (d == Direction.UP) {
            this.position.changeY(-speed);
        } else if (d == Direction.RIGHT) {
            this.position.changeX(speed);
        } else if (d == Direction.LEFT) {
            this.position.changeX(-speed);
        } else if (d == Direction.DOWN) {
            this.position.changeY(speed);
        }
    }

    public Position getPosition() {
        return position;
    }
}
