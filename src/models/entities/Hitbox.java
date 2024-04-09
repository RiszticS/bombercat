package models.entities;

import java.awt.*;
import java.util.ArrayList;
import models.Direction;

public class Hitbox {
    private final Rectangle top;
    private final Rectangle bottom;
    private final Rectangle left;
    private final Rectangle right;

    private final int w = 4;

    public Hitbox(int x, int y, int width, int height) {
        this.top = new Rectangle(x + w, y, width - 2*w, w);
        this.bottom = new Rectangle(x + w, y + height - w, width - 2*w, w);
        this.right = new Rectangle(x + width - w, y + w, w, height - 2*w);
        this.left = new Rectangle(x, y + w, w, height - 2*w);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(top.x, top.y, top.width, top.height);
        g2.setColor(Color.BLUE);
        g2.fillRect(bottom.x, bottom.y, bottom.width, bottom.height);
        g2.setColor(Color.GREEN);
        g2.fillRect(left.x, left.y, left.width, left.height);
        g2.setColor(Color.YELLOW);
        g2.fillRect(right.x, right.y, right.width, right.height);
    }

    public void changeX(int x) {
        top.x += x;
        bottom.x += x;
        left.x += x;
        right.x += x;
    }

    public void changeY(int y) {
        top.y += y;
        bottom.y += y;
        left.y += y;
        right.y += y;
    }

    public ArrayList<Direction> checkCollisionDirection(Hitbox other) {
        ArrayList<Direction> collidingDirections = new ArrayList<>();
        if (this.top.intersects(other.getBottom()) ||
                this.top.intersects(other.getLeft()) ||
                this.top.intersects(other.getRight()) ||
                this.top.intersects(other.getTop())
        ) { collidingDirections.add(Direction.UP); }
        if (this.bottom.intersects(other.getTop()) ||
                this.bottom.intersects(other.getRight()) ||
                this.bottom.intersects(other.getLeft()) ||
                this.bottom.intersects(other.getBottom())
        ) { collidingDirections.add(Direction.DOWN); }
        if (this.right.intersects(other.getLeft()) ||
                this.right.intersects(other.getTop()) ||
                this.right.intersects(other.getBottom()) ||
                this.right.intersects(other.getRight())
        ) { collidingDirections.add(Direction.RIGHT); }
        if (this.left.intersects(other.getRight()) ||
                this.left.intersects(other.getTop()) ||
                this.left.intersects(other.getBottom()) ||
                this.left.intersects(other.getLeft())
        ) { collidingDirections.add(Direction.LEFT); }

        return collidingDirections;
    }

    public boolean intersects(Hitbox other) {
        if (this.top.intersects(other.getBottom()) ||
            this.top.intersects(other.getTop()) ||
            this.top.intersects(other.getRight()) ||
            this.top.intersects(other.getLeft())
           ) { return true; }
        if (this.bottom.intersects(other.getBottom()) ||
            this.bottom.intersects(other.getTop()) ||
            this.bottom.intersects(other.getRight()) ||
            this.bottom.intersects(other.getLeft())
        ) { return true; }
        if (this.left.intersects(other.getBottom()) ||
            this.left.intersects(other.getTop()) ||
            this.left.intersects(other.getRight()) ||
            this.left.intersects(other.getLeft())
        ) { return true; }
        if (this.right.intersects(other.getBottom()) ||
            this.right.intersects(other.getTop()) ||
            this.right.intersects(other.getRight()) ||
            this.right.intersects(other.getLeft())
        ) { return true; }

        return false;
    }

    public Rectangle getTop() {
        return top;
    }

    public Rectangle getBottom() {
        return bottom;
    }

    public Rectangle getLeft() {
        return left;
    }

    public Rectangle getRight() {
        return right;
    }

    public int getX() {
        return  top.x + w;
    }

    public int getY() {
        return top.y + w;
    }
}
