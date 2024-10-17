package Classes;

import Utility.Enums.Coordinate;

public class Location {

    //col should always be first as per the chess notation (Col, Row)
    public Coordinate col;
    public Coordinate row;

    //creates a location given 2 letters
    public Location(Coordinate col, Coordinate rol) {
        this.row = row;
        this.col = col;
    }

    //creates a location given 2 ints
    public Location(int col, int row) {
        this.col = Coordinate.values[col];
        this.row = Coordinate.values[row];
    }

    //creates a location given a letter and an int
    public Location(Coordinate col, int row) {
        this.row = Coordinate.values[row];
        this.col = col;
    }

    //makes a location based on a string
    public Location(String location) {
        this.col = Coordinate.valueOf(location.substring(0,1));     //the first value (col) comes from the letter
        this.row = Coordinate.values[Integer.parseInt(location.substring(1,2))];        //second value (row) comes from the number
    }

    public int colIndex() {
        return col.ordinal();
    }
    public int rowIndex() {
        return row.ordinal();
    }

    @Override
    public String toString() {
        return col.name() + (row.ordinal() + 1);
    }
}
