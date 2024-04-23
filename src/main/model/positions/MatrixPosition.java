package main.model.positions;

public class MatrixPosition extends Position {
    public MatrixPosition(int row, int column) {
        super(row, column);
    }

    /**
     * Converts the MatrixPosition object into a CoordinatePosition object and returns it.
     * In order to calculate the CoordinatePosition, provide the size of a single tile or sprite.
     * Optionally, if the offsetX or offsetY parameters are not zero, the returned x and y
     * coordinates of the returned CoordinatePosition object will be adjusted appropriately.
     * @param tileSize Size of a single tile or sprite as an integer.
     * @return CoordinatePosition object.
     */
    public CoordinatePosition convertToCoordinatePosition(int tileSize) {
        int xCoordinate = this.y * tileSize;
        int yCoordinate = this.x * tileSize;
        return new CoordinatePosition(xCoordinate, yCoordinate);
    }
}
