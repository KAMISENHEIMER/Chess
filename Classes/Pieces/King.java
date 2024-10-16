package Classes.Pieces;

import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

public class King extends Piece {

    boolean hasMoved;

    public King(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public Move[] getMoves() {
        return new Move[0];
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "K";
    }
}
