package Utility;

public class Enums {

    //for storing/determining whether a piece is white or black
    public enum Color {
        White,
        Black
    }

    //pairs the location on the board with a letter.
    public enum Coordinate {
        A, B, C, D, E, F, G, H;

        //array of the values for getting a letter from an index
        public static final Coordinate[] values = Coordinate.values();
        }
}
