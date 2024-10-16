package Classes;

import Utility.Enums.Coordinate;

public class Location {

    public Coordinate row;
    public Coordinate col;

    //creates a location given 2 letters
    public Location(Coordinate row, Coordinate col) {
        this.row = row;
        this.col = col;
    }

    //creates a location given 2 ints
    public Location(int row, int col) {
        this.row = Coordinate.values[row];
        this.col = Coordinate.values[col];
    }

    //creates a location given a letter and an int
    public Location(Coordinate row, int col) {
        this.row = row;
        this.col = Coordinate.values[col];
    }

    public int rowIndex() {
        return row.ordinal();
    }
    public int colIndex() {
        return col.ordinal();
    }

    @Override
    public String toString() {
        return row.name() + (col.ordinal() + 1);
    }
}
