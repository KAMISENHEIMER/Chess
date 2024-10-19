package Classes;

import Utility.Enums.Coordinate;

public class Location {

    //col should always be first as per the chess notation (Col, Row)
    public Coordinate col;
    public Coordinate row;

    /**
     * Constructs a new location, given a column and row of coordinate type
     * @param col column of location
     * @param row row of location
     */
    public Location(Coordinate col, Coordinate row) {
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a new location, given a column and row of int type
     * @param col column of location
     * @param row row of location
     */
    public Location(int col, int row) {
        this.col = Coordinate.values[col];
        this.row = Coordinate.values[row];
    }

    /**
     * Constructs a new location, given a column coordinate and a row int
     * @param col column of location
     * @param row row of location
     */
    public Location(Coordinate col, int row) {
        this.row = Coordinate.values[row];
        this.col = col;
    }

    /**
     * Creates a new location, given a string representing that location
     *
     * @param location String representing new location Ex) "E4"
     */
    public Location(String location) {
        this.col = Coordinate.valueOf(location.substring(0,1));     //the first value (col) comes from the letter
        this.row = Coordinate.values[Integer.parseInt(location.substring(1,2))-1];        //second value (row) comes from the number
    }

    /**
     * Gets the index of a column, 0-7
     * @return column's index
     */
    public int colIndex() {
        return col.ordinal();
    }
    /**
     * Gets the index of a row, 0-7
     * @return row's index
     */
    public int rowIndex() {
        return row.ordinal();
    }

    /**
     * Converts a location to a string representing that location
     * @return String representing location Ex) "E4"
     */
    @Override
    public String toString() {
        return col.name() + (row.ordinal() + 1);
    }
}
