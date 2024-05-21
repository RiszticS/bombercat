package org.ctrlaltdefeat.model;

import org.ctrlaltdefeat.controllers.configuration.GraphicProperties;
import org.ctrlaltdefeat.controllers.configuration.ModelProperties;
import org.ctrlaltdefeat.model.positions.CoordinatePosition;

import java.awt.*;

public class Hitbox {
    private CoordinatePosition position;
    private final int width, height, offsetX, offsetY;
    private final Rectangle top, bottom, left, right;
    private final int hitboxThickness = ModelProperties.getHitboxThickness();

    /**
     * If the width and height parameters are not provided, the default hitbox size is the default tileSize - 2px
     * @param p The CoordinatePosition of the Hitbox.
     */
    public Hitbox(CoordinatePosition p) {
        this.position = p;
        this.width = GraphicProperties.getTileSize() - 2;
        this.height = GraphicProperties.getTileSize() - 2;
        this.offsetX = 0;
        this.offsetY = 0;
        this.top = new Rectangle(position.getX() + hitboxThickness, position.getY(), width - 2 * hitboxThickness, hitboxThickness);
        this.bottom = new Rectangle(position.getX() + hitboxThickness, position.getY() + height - hitboxThickness, width - 2 * hitboxThickness, hitboxThickness);
        this.right = new Rectangle(position.getX() + width - hitboxThickness, position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
        this.left = new Rectangle(position.getX(), position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
    }

    public Hitbox(CoordinatePosition p, int width, int height) {
        this.position = p;
        this.width = width;
        this.height = height;
        this.offsetX = 0;
        this.offsetY = 0;
        this.top = new Rectangle(position.getX() + hitboxThickness, position.getY(), width - 2 * hitboxThickness, hitboxThickness);
        this.bottom = new Rectangle(position.getX() + hitboxThickness, position.getY() + height - hitboxThickness, width - 2 * hitboxThickness, hitboxThickness);
        this.right = new Rectangle(position.getX() + width - hitboxThickness, position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
        this.left = new Rectangle(position.getX(), position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
    }

    /**
     * This is the offset version of the constructor of the Hitbox class. By default, the position of the
     * Hitbox is calculated from the upper-left corner of the tile that includes it. However, the Hitbox
     * can be offset along the X or Y axis using the offsetX and offsetY parameters accordingly.
     * @param p CoordinatePosition of the Hitbox calculated from the upper-left corner of the tile.
     * @param width Width of the Hitbox in integers.
     * @param height Height of the Hitbox in integers.
     * @param offsetX Offset along the X axis in integers.
     * @param offsetY Offset along the Y axis in integers.
     */
    public Hitbox(CoordinatePosition p, int width, int height, int offsetX, int offsetY) {
        this.position = new CoordinatePosition(p.getX() + offsetX, p.getY() + offsetY);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.top = new Rectangle(position.getX() + hitboxThickness, position.getY(), width - 2 * hitboxThickness, hitboxThickness);
        this.bottom = new Rectangle(position.getX() + hitboxThickness, position.getY() + height - hitboxThickness, width - 2 * hitboxThickness, hitboxThickness);
        this.right = new Rectangle(position.getX() + width - hitboxThickness, position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
        this.left = new Rectangle(position.getX(), position.getY() + hitboxThickness, hitboxThickness, height - 2 * hitboxThickness);
    }

    public boolean intersects (Hitbox other) {
        return intersectsFromBelow(other) ||
                intersectsFromAbove(other) ||
                intersectsFromLeft(other) ||
                intersectsFromRight(other);
    }

    public boolean intersectsFromBelow(Hitbox other) {
        return this.top.intersects(other.getBottom()) ||
                this.top.intersects(other.getLeft()) ||
                this.top.intersects(other.getRight());
    }

    public boolean intersectsFromAbove(Hitbox other) {
        return this.bottom.intersects(other.getTop()) ||
                this.bottom.intersects(other.getLeft()) ||
                this.bottom.intersects(other.getRight());
    }

    public boolean intersectsFromLeft(Hitbox other) {
        return this.right.intersects(other.getLeft()) ||
                this.right.intersects(other.getTop()) ||
                this.right.intersects(other.getBottom());
    }

    public boolean intersectsFromRight(Hitbox other) {
        return this.left.intersects(other.getRight()) ||
                this.left.intersects(other.getTop()) ||
                this.left.intersects(other.getBottom());
    }


    public void changeX(int x) {
        position.changeX(x);
        top.x += x;
        bottom.x += x;
        left.x += x;
        right.x += x;
    }

    public void changeY(int y) {
        position.changeY(y);
        top.y += y;
        bottom.y += y;
        left.y += y;
        right.y += y;
    }

    public CoordinatePosition getPosition() {
        return position;
    }
    public CoordinatePosition getCentre() {
        return new CoordinatePosition(position.getX() + (int) Math.floor((float) width / 2), position.getY() + (int) Math.floor((float) height / 2));
    }

    public void setPosition(CoordinatePosition p) {
        this.position = new CoordinatePosition(p.getX() + this.offsetX, p.getY() + this.offsetY);
        top.x = position.getX() + hitboxThickness;
        top.y = position.getY();
        bottom.x = position.getX() + hitboxThickness;
        bottom.y = position.getY() + height - hitboxThickness;
        left.x = position.getX();
        left.y = position.getY() + hitboxThickness;
        right.x = position.getX() + width - hitboxThickness;
        right.y = position.getY() + hitboxThickness;
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
}
