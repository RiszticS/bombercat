package main.model.positions;

public class CoordinatePosition extends Position {
    public CoordinatePosition(int x, int y) {
        super(x, y);
    }

    public void changeX(int x) {
        this.x += x;
    }

    public void changeY(int y) {
        this.y += y;
    }


    /**
     * Converts the CoordinatePosition object into a MatrixPosition object and returns it.
     * In order to calculate the MatrixPosition, provide the size of a single tile or sprite.
     * Optionally, the x and y coordinates of the CoordinatePosition object can be offset
     * using the offsetX and offsetY parameters. In this case, the MatrixPosition will be
     * calculated from the offset CoordinatePosition. Otherwise, set them to be zero.
     * @param tileSize Size of a single tile or sprite as an integer.
     * //@param offsetX Offset the x coordinate by this value.
     * //@param offsetY Offset the y coordinate by this value.
     * @return MatrixPosition object.
     */
    public MatrixPosition convertToMatrixPosition(int tileSize) {
        int matrixPositionRowIndex = (int)Math.floor((double) (this.y) / tileSize);
        int matrixPositionColumnIndex = (int)Math.floor((double) (this.x) / tileSize);
        return new MatrixPosition(matrixPositionRowIndex, matrixPositionColumnIndex);
    }
}

